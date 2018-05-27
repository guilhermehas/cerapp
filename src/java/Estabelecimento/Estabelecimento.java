/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estabelecimento;

import auxiliar.objetoDados;
import auxiliar.CervejaEstabelecimento;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.*;

/**
 *
 * @author guilherme
 */
public class Estabelecimento implements objetoDados {
    int id;
    String nomeOficial;
    String nomeComercial;
    String CNPJ;
    String senha;
    String email;
    float latitude;
    float longitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    /**
     *
     * @param senha
     * @throws Exception
     */
    public void setSenha(String senha) throws Exception {
        int t = senha.length();
        if(t<5 || t>20)
            throw new Exception("Tamanho de senha invalida");
        this.senha = senha;
    }
    
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public String getNomeOficial(){
        return this.nomeOficial;
    }
    public void setNomeOficial(String nomeOficial){
        this.nomeOficial = nomeOficial;
    }
    
    public String getNomeComercial(){
        return this.nomeComercial;
    }
    public void setNomeComercial(String nomeComercial){
        this.nomeComercial = nomeComercial;
    }
    
    public String getCNPJ(){
        return this.CNPJ;
    }
    public void setCNPJ(String CNPJ){
        this.CNPJ = CNPJ;
    }
    
    public JsonObject toJSON(){
        JsonObject json = (JsonObject) Json.createObjectBuilder()
                    .add("email", getEmail())
                    .add("nomeOficial", getNomeOficial())
                    .add("nomeComercial", getNomeComercial())
                    .add("cnpj", getCNPJ())
                    .add("latitude", getLatitude())
                    .add("longitude", getLongitude())
                    .build();
        
        return json;
    }
	
	public ArrayList<CervejaEstabelecimento> getCervejas() throws ClassNotFoundException{
		EstabelecimentoDAO edao = new EstabelecimentoDAO();
		ArrayList<CervejaEstabelecimento> cervs = edao.selecionaCerveja(this);
		return cervs;
	}

	@Override
	public boolean save() {
		try {
			(new EstabelecimentoDAO()).insere(this);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Estabelecimento.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean update() {
		try {
			(new EstabelecimentoDAO()).atualiza(this);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Estabelecimento.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete() {
		try {
			(new EstabelecimentoDAO()).delete(this);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Estabelecimento.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}
    
}
