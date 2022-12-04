package com.disney.teams.service.logs;

public class TraceContextUtils {

    public static final String RID_HTTP_HEADER_KEY = "X-Request-Id";

    private static final ThreadLocal<TraceLinkWrapper> TRACE_ID_THREAD_LOCAL = new ThreadLocal<>();

    private static final AutoRemove<TraceLinkWrapper> AUTO_REMOVE = new AutoRemove<>(TRACE_ID_THREAD_LOCAL);

    public static AutoRemove<TraceLinkWrapper> use(TraceLinkWrapper wrapper) {
        set(wrapper);
        return AUTO_REMOVE;
    }

    public static void set(TraceLinkWrapper wrapper) {
        TRACE_ID_THREAD_LOCAL.set(wrapper);
    }

    public static void remove() {
        TRACE_ID_THREAD_LOCAL.remove();
    }

    public static TraceLinkWrapper get() {
        return TRACE_ID_THREAD_LOCAL.get();
    }

    public static String traceLink() {
        TraceLinkWrapper wrapper = get();
        return wrapper == null ? null : wrapper.traceLink();
    }

    public static String logLink() {
        TraceLinkWrapper wrapper = get();
        return wrapper == null ? null : wrapper.logLink();
    }

    public static TraceLinkWrapper autoGenerateIfNotExists(RequestEntry entry) {
        TraceLinkWrapper tid = TRACE_ID_THREAD_LOCAL.get();
        return tid == null ? generateAndGet(entry) : tid;
    }

    public static TraceLinkWrapper generateAndGet(RequestEntry entry) {
        TraceLinkWrapper tid = TraceLinkWrapper.translateTraceLink(null, entry);
        TRACE_ID_THREAD_LOCAL.set(tid);
        return tid;
    }

    private static AutoRemove<TraceLinkWrapper> traceEntry(String traceLink, RequestEntry entry, String context) {
        TraceLinkWrapper traceLinkWrapper = TraceLinkWrapper.translateTraceLink(traceLink, entry);
        traceLinkWrapper.splitSpan();
        return use(traceLinkWrapper);
    }

    public static TraceLinkWrapper newRequest(RequestEntry entry) {
        TraceLinkWrapper wrapper = autoGenerateIfNotExists(entry);
        return wrapper.incrSpan();
    }

    public static AutoRemove<TraceLinkWrapper> backgroundEntry(String traceLink, String context) {
        return traceEntry(traceLink, RequestEntry.b, context);
    }

    public static AutoRemove<TraceLinkWrapper> httpEntry(String traceLink, String context) {
        return traceEntry(traceLink, RequestEntry.h, context);
    }

    public static AutoRemove<TraceLinkWrapper> grpcEntry(String traceLink, String context) {
        return traceEntry(traceLink, RequestEntry.g, context);
    }

    public static AutoRemove<TraceLinkWrapper> canalEntry(String traceLink, String context) {
        return traceEntry(traceLink, RequestEntry.c, context);
    }

    public static AutoRemove<TraceLinkWrapper> mqEntry(String traceLink, String context) {
        return traceEntry(traceLink, RequestEntry.m, context);
    }

    public static AutoRemove<TraceLinkWrapper> quartzEntry(String traceLink, String context) {
        return traceEntry(traceLink, RequestEntry.q, context);
    }
}