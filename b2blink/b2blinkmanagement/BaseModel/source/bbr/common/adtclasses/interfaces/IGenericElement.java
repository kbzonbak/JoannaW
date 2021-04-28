package bbr.common.adtclasses.interfaces;

import java.io.Serializable;

/**
 * Esta interface representa las entidades con llave primaria propia
 * 
 * @author dvillanueva
 * 
 */
public interface IGenericElement<ID> extends Serializable {

    ID getKey();

    void setKey(ID key);

}
