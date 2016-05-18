package org.write_day.domain.dto;

import org.write_day.components.enums.CommandStatus;
import org.write_day.components.enums.FriendStatus;

public class ResponseResult {

    public String result;

    public String status = "OK";

    public ResponseResult(String result, String status) {
        this.result = result;
        this.status = status;
    }

    public ResponseResult(String result) {
        this.result = result;
    }

    public ResponseResult() {
    }

    public ResponseResult(CommandStatus result) {
        this.result = result.toString();
    }

    public ResponseResult(FriendStatus friendStatus) {
        result = friendStatus.name();
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setResult(CommandStatus result) {
        this.result = result.toString();
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
