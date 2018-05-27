/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author guilherme
 */
public class CervejaResultado {
	String nome;
	String estabelecimento;
	int eid;
	int distancia;
	float preco;
	float latitude;
	float longitude;
	
	public JsonObject toJson(){
        JsonObject json = (JsonObject) Json.createObjectBuilder()
            .add("nome", getNome())        
            .add("estabelecimento", getEstabelecimento())
			.add("eid",getEid())
			.add("distancia", getDistancia())
			.add("preco", getPreco())
			.add("latitude", getLatitude())
			.add("longitude", getLongitude())
            .build();
        
        return json;
    }

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
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
	
	public void setDistancia(float latitude, float longitude) {
		float dist =  Calculo.distFrom(latitude,longitude,this.latitude,this.longitude);
		dist /= 1000;
		System.out.println(dist);
		setDistancia((int) dist);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public String getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
}
