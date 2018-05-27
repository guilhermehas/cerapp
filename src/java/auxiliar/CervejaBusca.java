/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import cerveja.CervejaDAO;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author guilherme
 */
public class CervejaBusca {
	float latitude;
	float longitude;
	int distancia;
	float precoMaximo;
	String nome;
	
	public ArrayList<CervejaResultado> getCervejas() throws ClassNotFoundException{
		CervejaDAO cdao = new CervejaDAO();
		ArrayList<CervejaResultado> cervCand = cdao.getCervejaResultado(this);
		ArrayList<CervejaResultado> cs = new ArrayList<>();
		
		for(CervejaResultado c: cervCand){
			c.setDistancia(getLatitude(), getLongitude());
			if(c.getDistancia() <= getDistancia()){
				cs.add(c);
			}
				
		}
		
		return cs;
	}
	
	public JsonArray toJsonResult() throws ClassNotFoundException{
		ArrayList<CervejaResultado> cs = getCervejas();
		
		JsonArrayBuilder jsonbuilder = Json.createArrayBuilder();
			
		cs.stream().forEach((it) -> {
			jsonbuilder.add(it.toJson());
		});
			
		JsonArray json = jsonbuilder.build();
		
		return json;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if(nome != null && nome.length() > 0)
			this.nome = nome;
	}

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

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public float getPrecoMaximo() {
		return precoMaximo;
	}

	public void setPrecoMaximo(float precoMaximo) {
		this.precoMaximo = precoMaximo;
	}
}
