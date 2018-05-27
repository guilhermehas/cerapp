/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import Estabelecimento.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author guilherme
 */
public class LoginController {
    
    public boolean efetuaLogin(Estabelecimento e, HttpSession session) throws ClassNotFoundException{
		EstabelecimentoDAO edao = new EstabelecimentoDAO();
		 if(edao.existeUsuario(e)){
			 session.setMaxInactiveInterval(-1);
			 session.setAttribute("user",edao.selecionaPorEmail(e));
			 return true;
		 }
		 return false;
    }
	
	/**
	 *
	 * @param session
	 * @return
	 */
	public boolean logOut(HttpSession session){
		session.invalidate();
		return true;
    }
    
}
