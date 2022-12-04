package com.disney.teams.service.logs;

import org.apache.http.util.Asserts;

import java.io.Serializable;
import java.util.UUID;

public class TraceLinkWrapper implements Serializable, Cloneable {

    private static final long serialVersionUID = 7422210080790743170L;

    public static final char TRACE_GAP = ':';

    public static final String SPAN_GAP = ".";

    private final static String LOG_LINK_TEMPLATE = "%s" + TRACE_GAP + "%s";

    private final static String TRACE_LINK_TEMPLATE = LOG_LINK_TEMPLATE + SPAN_GAP + "%s";

    public static String DEFAULT_SERVER_ID = "undefined";

    public static String DEFAULT_P_SPAN_ID = "0";

    /**
     * traceId
     */
    private String traceId;
    /**
     * stay unchanged when inherit from parent traceLink,set to 0 in schedule task when null
     */
    private String pSpanId = null;
    /**
     * represent calling counts of outside services, including rpc and http
     */
    private Integer spanId = 0;

    private RequestEntry requestEntry = RequestEntry.b;

    public static TraceLinkWrapper translateTraceLink(String traceLinker) {
        return translateTraceLink(traceLinker, RequestEntry.b);
    }

    public static TraceLinkWrapper translateTraceLink(String traceLinker, RequestEntry requestEntry) {
        TraceLinkWrapper traceLinkWrapper = new TraceLinkWrapper();
        traceLinkWrapper.requestEntry = requestEntry;
        if (traceLinker == null || traceLinker.length() == 0) {
            traceLinkWrapper.traceId = UUID.randomUUID().toString().replace("-", "");
            return traceLinkWrapper;
        }
        traceLinker = traceLinker.replaceAll("^[:.]+", "")
                .replaceAll("[:.]+$", "");
        int index = traceLinker.indexOf(TRACE_GAP);
        if (index < 0) {
            traceLinkWrapper.traceId = traceLinker;
        } else {
            try {
                traceLinkWrapper.traceId = traceLinker.substring(0, index);
                int firstIndex = traceLinker.indexOf(SPAN_GAP);
                int lastIndex = traceLinker.lastIndexOf(SPAN_GAP);
                if (lastIndex != firstIndex) {
                    traceLinkWrapper.pSpanId = traceLinker.substring(index + 1, lastIndex);
                    traceLinkWrapper.spanId = Integer.parseInt(traceLinker.substring(lastIndex + 1));
                } else if (firstIndex == -1) {
                    traceLinkWrapper.pSpanId = traceLinker.substring(index + 1);
                } else {
                    traceLinkWrapper.pSpanId = traceLinker.substring(index + 1, firstIndex);
                    traceLinkWrapper.spanId = Integer.parseInt(traceLinker.substring(firstIndex + 1));
                }
            } catch (RuntimeException e) {
                traceLinkWrapper.pSpanId = null;
                traceLinkWrapper.spanId = null;
                e.printStackTrace();
            }
        }
        return traceLinkWrapper;
    }

    private TraceLinkWrapper() {
    }

    public TraceLinkWrapper incrSpan() {
        spanId++;
        return this;
    }

    /**
     * 请求入口使用，包括HTTP,RPC,MQ
     * pSpanId == null 请求来自client，分叉的时候使用spanId作为pSpanId
     * spanId == null 只有定时任务等服务自己发起的请求分叉才会发生
     *
     * @return
     */
    public TraceLinkWrapper splitSpan() {
        Asserts.check(spanId != null, "spanId == null");
        if (spanId == null) {
            return null;
        }
        //请求入口使用，包括
        if (pSpanId == null) {
            pSpanId = String.valueOf(spanId);
        } else {
            pSpanId = pSpanId + "." + spanId;
        }
        spanId = 1;
        return this;
    }

    /**
     * used for transmission to other services
     */
    public String traceLink() {
        if (pSpanId != null) {
            /**
             * called from outside
             */
            return spanId != null ?
                    String.format(TRACE_LINK_TEMPLATE, this.traceId, this.pSpanId, this.spanId) :
                    String.format(LOG_LINK_TEMPLATE, this.traceId, this.spanId);
        }
        /**
         * called by schedule service
         */
        return String.format(LOG_LINK_TEMPLATE, this.traceId, this.spanId);
    }

    /**
     * used for log function
     */
    public String logLink() {
        if (pSpanId != null) {
            return String.format(LOG_LINK_TEMPLATE, this.traceId, this.pSpanId);
        }
        return this.traceId;
    }

    public TraceLinkWrapper clone() {
        TraceLinkWrapper traceLinkWrapper = new TraceLinkWrapper();
        traceLinkWrapper.traceId = this.traceId;
        traceLinkWrapper.spanId = this.spanId;
        traceLinkWrapper.requestEntry = this.requestEntry;
        return traceLinkWrapper;
    }

    @Override
    public String toString() {
        return traceLink();
    }
}
