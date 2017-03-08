package org.rulez.demokracia.pdengine.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class ReportedException extends Exception {

	private static final long serialVersionUID = 3322550743512295289L;

	private List<String> additionalDetails = new ArrayList<String>();

    public ReportedException (String message) {
        super (message);
    }

    public ReportedException (String message, String detail) {
        super (message);
        additionalDetails.add(detail);
    }

    public ReportedException (Throwable cause) {
        super (cause);
    }

    public ReportedException (String message, Throwable cause) {
        super (message, cause);
    }
    
    public String getMessage() {
    	if (additionalDetails.isEmpty()) {
    		return super.getMessage();
    	}
    	return MessageFormat.format(super.getMessage(), additionalDetails.get(0));
    }

	public String toJSONString() {
		JSONObject jsonObject = toJSON();
		return jsonObject.toString(1);
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", super.getMessage());
		jsonObject.put("details", additionalDetails);
		return jsonObject;
	}

}
