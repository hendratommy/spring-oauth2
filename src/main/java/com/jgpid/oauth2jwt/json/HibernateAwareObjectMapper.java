package com.jgpid.oauth2jwt.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Created by hendr on 02/07/2017.
 */
/**
 * Do not delete! Required by JBOSS EAP/Wildfly
 * @author hendr
 *
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 1L;

    public HibernateAwareObjectMapper() {
        Hibernate5Module hm = new Hibernate5Module();
        registerModule(hm);
    }
}
