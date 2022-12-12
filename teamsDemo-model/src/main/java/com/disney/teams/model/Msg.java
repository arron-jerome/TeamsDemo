package com.disney.teams.model;

import com.disney.teams.model.exception.StatusCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "Msg")
@XmlAccessorType(XmlAccessType.FIELD)
public class Msg implements Serializable {

    private static final long serialVersionUID = -6105070616503910307L;

    private static final int DEFAULT_MAP_SIZE = 1;
    
    public static final String EMPTY = "";

    private static final String OK_CODE = StatusCode.SUCCESS_CODE;

    private static final String SERVER_ERROR_CODE = StatusCode.SERVER_ERROR_CODE;
    
    /**
     * 统计数量接口返回数据的key值
     */
    public static final String DATA_COUNT_KEY = "count";
    
    /**
     * 是否存在接口返回数据的key值
     */
    public static final String DATA_EXISTS_KEY = "exists";

	/**
	 * 数据列表的key值
	 */
	public static final String DATA_LIST_KEY = "list";

	/**
	 * 分页数据的key值
	 */
	public static final String DATA_PAGE_KEY = "page";
    
    /**
     * 新增数据对应的主键ID对应key值
     */
    public static final String DATA_CREATE_ID_KEY = "createId";
    
    /**
     * 返回服务端新增数据的key值
     */
    public static final String DATA_NEW_DATA_KEY = "newData";
    
    /**
     * 返回服务端前一版本数据的key值
     */
    public static final String DATA_OLD_DATA_KEY = "oldData";

	private static RidContext ridContext;

    // 返回状态码
	private String code;
	
	/**
	 * 请求状态描述
	 */
	private String msg = EMPTY;

	//当前请求对应的标识，用于跟踪定位整个请求调用过程
	private String rid;

	// 返回信息
	private Object data;

	public Msg() {
		super();
		if(ridContext != null) {
			this.rid = ridContext.get();
		}
	}
	
	public Msg(boolean ok){
	    this();
		this.code = ok ? OK_CODE : SERVER_ERROR_CODE;
	}

	public Msg(boolean ok, String msg) {
	    this(ok);
	    this.msg = msg;
	}

	public Msg(boolean ok, String msg, Object data) {
	    this(ok, msg);
	    this.data = data;
	}
	
	public Msg(String code) {
        this();
        this.code = code;
    }

	public Msg(String code, String msg) {
		this(code);
		this.msg = msg;
	}

	public Msg(String code, String msg, Object data) {
		this(code, msg);
		this.data = data;
	}

	public Msg(StatusCode code) {
		this();
		this.code = code.getCode();
		this.msg = code.getMessage();
	}

    public Msg(StatusCode code, Object data) {
        this(code);
		this.data = data;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
    public void setMsg(String msg) {
        this.msg = (msg == null ? EMPTY : msg);
    }

    public String getMsg() {
        return msg;
    }

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public void setData(Object data){
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	/**
	 * use statis isOk method, {@link Msg#isOk(Msg)}
	 * @return
	 */
	@Deprecated
	public boolean assertOk(){
	    return OK_CODE.equals(code);
	}
	
	@SuppressWarnings("unchecked")
    public <T> T dataValue(String key) {
	    if(data == null){
	        return null;
	    }
	    return (T)((Map<String, ?>)data).get(key);
	}

	public static Msg msg(boolean ok){
	    return new Msg(ok);
	}

	public static Msg msg(boolean ok, String msg){
	    return new Msg(ok, msg);
	}

	public static Msg msg(boolean ok, String msg,Object data){
		return new Msg(ok, msg,data);
	}

	public static Msg msg(StatusCode code){
	    return new Msg(code);
	}

	public static Msg msg(StatusCode code,Object data){
		return new Msg(code,data);
	}

	public static Msg msg(String code, String msg){
	    return new Msg(code, msg);
	}

	public static Msg msg(String code, String msg,Object data){
		return new Msg(code, msg,data);
	}

	@Deprecated
	public static Msg msg(StatusCode code, String msg){
	    return new Msg(code, msg);
	}

	public static Msg ok(){
		return msg(true);
	}

	public static Msg ok(Object data){
		return msg(StatusCode.SUCCESS).data(data);
	}

	public static Msg failed(){
		return msg(false);
	}

	public static Msg failed(String msg){
		return msg(false, msg);
	}

	public static Msg failed(String code, String msg){
		return msg(code, msg);
	}
	public static Msg failed(String code, String msg, Object data){
		return msg(code, msg).data(data);
	}

	public static Msg failed(StatusCode code){
	    return msg(code);
	}

	public static Msg failed(StatusCode code, Object data){
		return msg(code).data(data);
	}

	@Deprecated
	public static Msg failed(StatusCode code, String msg){
	    return msg(code, msg);
	}

	public static boolean isOk(Msg msg){
	    return null == msg ? false : OK_CODE.equals(msg.getCode());
	}
	public static boolean isOk(String code){
		return OK_CODE.equals(code);
	}
	public static boolean isOk(StatusCode code){
		return code == null ? false : OK_CODE.equals(code.getCode());
	}

	public static boolean isNotOk(Msg msg){
	    return !isOk(msg);
	}

	public static boolean isNotOk(String code){
		return !isOk(code);
	}

	public static boolean isNotOk(StatusCode code){
		return !isOk(code);
	}

	public static boolean isAllOk(Collection<Msg> msgs) {
		if(msgs == null || msgs.isEmpty()) {
			return true;
		}
		for(Msg msg : msgs) {
			if(isNotOk(msg)) {
				return false;
			}
		}
		return true;
	}

	public Msg code(String code){
		this.code = code;
		return this;
	}
	
	public Msg code(StatusCode code){
	    this.code = code.getCode();
	    return this;
	}

	public Msg rid(String rid) {
		this.rid = rid;
		return this;
	}

	public Msg msg(String msg){
	    setMsg(msg);
	    return this;
	}

	public Msg data(Object data){
		this.data = data;
		return this;
	}

	public <T> T data(){
		return (T)data;
	}

	public Msg createIdInfo(Object createId){
	    return info(DATA_CREATE_ID_KEY, createId);
	}

	public Msg count(int count){
	    return info(DATA_COUNT_KEY, count);
	}

	public Msg exists(boolean exists){
	    return info(DATA_EXISTS_KEY, exists);
	}

	public Msg info(String key, Object value){
	    if(data == null){
	        data = new HashMap<String, Object>(DEFAULT_MAP_SIZE);
	    }
		((Map)data).put(key, value);
	    return this;
	}

	public Msg list(Object value){
		return info(DATA_LIST_KEY, value);
	}

	public Msg page(Object value){
		return info(DATA_PAGE_KEY, value);
	}

	public Msg updateInfo(Object oldData, Object newData){
		updateNewInfo(newData);
		((Map)data).put(DATA_OLD_DATA_KEY, oldData);
        return this;
	}

	public Msg updateNewInfo(Object newData){
	    return info(DATA_NEW_DATA_KEY, newData);
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Msg [code=").append(code)
        .append(", msg=").append(msg)
        .append(", data=").append(data)
        .append("]");
        return sb.toString();
    }

	public String toJsonStringWithoutData(){
		String format = "{\"code\":\"%s\",\"msg\":\"%s\", \"rid\":%s}";
		return String.format(format, code, msg == null ? "" : msg, rid == null ? null : "\"" + rid + "\"");
	}

	public static Msg parseFromJsonStringWithoutData(String json){
		if(json == null){
			return null;
		}
		String code = null;
		int codeIndex = json.indexOf("code");
		if(codeIndex >= 0 ){
			codeIndex += 7;
			int quioteIndex = json.indexOf('"', codeIndex);
			if(quioteIndex >= 0){
				code = json.substring(codeIndex, quioteIndex);
			}
		}

		String rid = null;
		int ridIndex = json.indexOf("rid");
		if(ridIndex >= 0 ){
			ridIndex += 6;
			int quioteIndex = json.indexOf('"', ridIndex);
			if(quioteIndex >= 0){
				rid = json.substring(ridIndex, quioteIndex);
			}
		}

		String msg = "";
		int msgIndex = json.indexOf("msg");
		if(msgIndex >= 0){
			msgIndex += 6;
			int quioteIndex = msgIndex;
			while((quioteIndex = json.indexOf('"', quioteIndex)) >= 0){
				if(json.charAt(quioteIndex - 1) != '\\'){
					msg = json.substring(msgIndex, quioteIndex);
					break;
				}
			}
		}
		return msg(code, msg).rid(rid);
	}

	public static interface RidContext {
		String get();
	}

	public static void registerRidContextListener(RidContext context) {
		Msg.ridContext = context;
	}

}
