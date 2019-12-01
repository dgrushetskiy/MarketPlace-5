package com.senla.kedaleanid.controller.configuration.exceptionhandler;

import com.senla.kedaleanid.controller.configuration.exceptionhandler.errormodel.ApiError;
import com.senla.kedaleanid.utility.exception.*;
import com.senla.kedaleanid.utility.mapper.exception.IsNotConvertibleException;
import com.senla.kedaleanid.utility.mapper.exception.WrongReferencedTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private ApiError buildError(HttpStatus status, RuntimeException ex) {
        ApiError apiError = new ApiError(status);
        apiError.setDebugMessage(ex.getLocalizedMessage());
        apiError.setMessage(ex.getMessage());

        return apiError;
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


    @ExceptionHandler(NoDbRecordException.class)
    protected ResponseEntity<Object> handleEntityNotFound(NoDbRecordException ex) {
        return buildResponseEntity(buildError(NOT_FOUND, ex));
    }

    @ExceptionHandler(NullDtoObjectException.class)
    protected ResponseEntity<Object> handleNullDto(NullDtoObjectException ex) {
        return buildResponseEntity(buildError(BAD_REQUEST, ex));
    }

    @ExceptionHandler({ClassConstructorException.class, com.senla.kedaleanid.utility.mapper.exception.ClassConstructorException.class})
    protected ResponseEntity<Object> handleConstructorExc(RuntimeException ex) {
        return buildResponseEntity(buildError(INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(IsNotConvertibleException.class)
    protected ResponseEntity<Object> handleNotConvertible(IsNotConvertibleException ex) {
        return buildResponseEntity(buildError(INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(WrongReferencedTypeException.class)
    protected ResponseEntity<Object> handleReferencedTypeExc(WrongReferencedTypeException ex) {
        return buildResponseEntity(buildError(INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(InvocationException.class)
    protected ResponseEntity<Object> handleInvocationExc(InvocationException ex) {
        return buildResponseEntity(buildError(INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(RedirectFailedException.class)
    protected ResponseEntity<Object> handleRedirectExc(RedirectFailedException ex) {
        return buildResponseEntity(buildError(INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(TransactionFailureException.class)
    protected ResponseEntity<Object> handleEntityNotFound(TransactionFailureException ex) {
        return buildResponseEntity(buildError(INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    protected ResponseEntity<Object> handleRecordExists(RecordAlreadyExistsException ex) {
        return buildResponseEntity(buildError(CONFLICT, ex));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex) {
        return buildResponseEntity(buildError(INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(ClassCastException.class)
    protected ResponseEntity<Object> handleClassCastException(ClassCastException ex) {
        return buildResponseEntity(buildError(INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        String error = "Wrong argument received!";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleArgumentMismatch(MethodArgumentTypeMismatchException ex) {
        String error = "Wrong argument received!";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCreds(BadCredentialsException ex) {
        String error = "Authentication required";
        return buildResponseEntity(new ApiError(UNAUTHORIZED, error, ex));
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        String error = "Access denied!";
        return buildResponseEntity(new ApiError(FORBIDDEN, error, ex));
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    protected ResponseEntity<Object> handleUnexpectedType(UnexpectedTypeException ex) {
        String error = "Validation error (probably)";
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        logger.info("[" + servletWebRequest.getHttpMethod() + "] to [" + servletWebRequest.getRequest().getServletPath() + "]");
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage("Could not find the " + ex.getHttpMethod() + " method for URL " + ex.getRequestURL());
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Method is not supported: " + ex.getMethod());
        apiError.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }
}