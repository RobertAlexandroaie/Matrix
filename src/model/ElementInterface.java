/**
 * 
 */
package model;

/**
 * @author Robert
 */
public interface ElementInterface {
    public abstract ElementInterface sum(ElementInterface obj);

    public abstract ElementInterface mul(ElementInterface obj);

    public abstract ElementInterface sub(ElementInterface obj);

    public abstract ElementInterface div(ElementInterface obj);

    public abstract ElementInterface getRandom();

    public abstract ElementInterface getDefaultElement();
}
