/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.Exception;

/**
 *
 * @author bonde
 */
public class MTDalException extends Exception
{
    /**
     * A cunstructor of the exception class MTDalException
     */
    public MTDalException() 
    {
        super();
    }
    
    /**
     * A cunstructor of the exception class MTDalException class. 
     * Throws a message if an exception has been catched
     * @param message the message getting thrown
     */
    public MTDalException(String message)
    {
        super(message);
    }
    
    /**
     * A cunstructor of the exception class MTDalException class.
     * Throws a message and the exception if an exception has been catched
     * @param message the message getting thrown
     * @param ex the exception getting thrown
     */
    public MTDalException(String message, Exception ex)
    {
        super(message, ex);
    }
}
