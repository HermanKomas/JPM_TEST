package com.jpm.trading.extract.marshalling;

/**
 * The MappingException wraps exceptions related instruction JSONs to SettlemntDto conversions which is likely
 * to occur as instructions are sources from outside systems that we have no control over
 *
 * @author HKomas
 */
public class MappingException extends Exception {

    public MappingException(String message){
        super("Settlement Instruction Mapping Exception. Message: " + message);
    }
}
