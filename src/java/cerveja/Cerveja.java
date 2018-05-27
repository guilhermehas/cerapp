/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cerveja;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author guilherme
 */
public class Cerveja {
    int id;
    String nome;
    String tipo;
    float porcentagem;
    String pais;
    String fabricante;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(float porcentagem) {
        this.porcentagem = porcentagem;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
	
	public JsonObject toJson(){
		JsonObject json = (JsonObject) Json.createObjectBuilder()
					.add("id",getId())
					.add("nome", getNome())
					//.add("fabricante", getFabricante())
					//.add("tipo",getTipo())
					//.add("porcentagem", getPorcentagem())
					//.add("pais", getPais())
					.build();
		//System.out.print(json);
		
		return json;
	}
}
