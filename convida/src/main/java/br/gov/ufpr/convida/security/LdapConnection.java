package br.gov.ufpr.convida.security;


import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.Context;
import javax.naming.NamingException;
import java.lang.String;

import java.util.Hashtable;



public class LdapConnection {

    public boolean connectToLDAP(String user, String password) throws NamingException{
        try{
            System.out.println("CHEGUEI AQUI PO --------");

            String[] u = user.split("@");
            System.out.println(" ----- NOME ------------- : " + u[0]);
            

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://ldap.agtic.ufpr.br:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "uid="+u[0]+" , ou=people, dc=ufpr,dc=br");
        env.put(Context.SECURITY_CREDENTIALS, password);
       
        //"+user+ "
       
        DirContext ctx = new InitialDirContext(env);
        
        System.out.println(" ---------------- Success---------------------");
        
        ctx.close();
        return true;   
        
        }catch (NamingException e){
            e.printStackTrace();
            return false;
        }

      }
}
