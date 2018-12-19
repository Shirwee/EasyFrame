package com.shirwee.easyframe.net;

/**
 * 描述：自定义异常
 *
 * @author shirwee
 */
public class MessageException extends Exception {
    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }
}
