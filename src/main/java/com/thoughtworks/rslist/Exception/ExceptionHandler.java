package com.thoughtworks.rslist.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
  @org.springframework.web.bind.annotation.ExceptionHandler({StartEndException.class, RsIndexException.class})
  public ResponseEntity<CommentError> handleIndexOutOfRangeException(Exception ex){
    if (ex instanceof StartEndException){
      CommentError commentError = new CommentError();
      commentError.setError(ex.getMessage());
      return ResponseEntity.badRequest().body(commentError);
    }

    if (ex instanceof RsIndexException){
      CommentError commentError = new CommentError();
      commentError.setError(ex.getMessage());
      return ResponseEntity.badRequest().body(commentError);
    }

    CommentError commentError = new CommentError();
    commentError.setError("Invalid Param");
    return ResponseEntity.badRequest().body(commentError);
  }
}
