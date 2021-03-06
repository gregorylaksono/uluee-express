package org.uluee.web.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class CallSOAPAction {
	private String method_name;
	private LinkedHashMap<String, Object> param;
	private final static Logger LOG = Logger.getLogger(CallSOAPAction.class.getName());
	private ISOAPResultCallBack callBack;
	public static final String NAMESPACE = "http://service.act.de";
	public static final String SUCCESS_CODE = "00:success";
	public CallSOAPAction(LinkedHashMap<String, Object> param, String method_name, ISOAPResultCallBack callback){
		this.param = param;
		this.method_name = method_name;
		this.callBack = callback;
		
		doCall();
	}

	private void doCall() {
		SoapObject repaymentReq = new SoapObject(NAMESPACE,
				method_name);
		LOG.info("Call service "+method_name);
		for(Map.Entry<String, Object> m : param.entrySet()){
			Object o = m.getValue();
			if(o instanceof String[]){
				String[] arr = (String[]) o;
				for(Object s : arr){
					if(s instanceof String) {
						repaymentReq.addProperty(m.getKey(), s);
					}
					else if(s instanceof String[]) {
						String[] array =  (String[]) s;
						for(String t : array) {
							repaymentReq.addProperty(m.getKey(), t);
						}
					}
				}
			}else{
				repaymentReq.addProperty(m.getKey(), o);				
			}
			LOG.info(m.getKey()+" -- "+o);
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.setOutputSoapObject(repaymentReq);
		envelope.dotNet = true;

		try {
			HttpTransportSE httpTransport = new HttpTransportSE(Constant.WS_PATH);

			httpTransport.call("http://service.act.de/"+method_name, envelope);
			Object o = envelope.bodyIn;
			String statusCode = "Cannot connect to host server";
			if(o instanceof SoapObject){
				SoapObject getCommodityResponse = (SoapObject) o;
				if (getCommodityResponse != null) {
					SoapObject ActernityResponse = (SoapObject) getCommodityResponse
							.getProperty(0);
					
					statusCode = ActernityResponse.getProperty("code")
							.toString();
					SoapObject data = null;
					if (statusCode.equalsIgnoreCase(SUCCESS_CODE)) {
						if(ActernityResponse.getProperty("data") instanceof SoapObject){
							data = (SoapObject) ActernityResponse
									.getProperty("data");							
						}else if(ActernityResponse.getProperty("data") instanceof SoapPrimitive){
							SoapPrimitive dataPrimitive = (SoapPrimitive) ActernityResponse
									.getProperty("data");
							data = new SoapObject(dataPrimitive.getNamespace(), dataPrimitive.getName());
							data.addAttribute("value", dataPrimitive.toString());
						}
						callBack.handleResult(data,statusCode);					
					}else{
						callBack.handleError(statusCode);
					}
				}
			}else{
				callBack.handleError(statusCode);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	
	public interface ISOAPResultCallBack{
		public void handleResult(SoapObject data, String statusCode);
		public void handleError(String statusCode);
	}
	
}
