/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import cerveja.Cerveja;
import cerveja.CervejaDAO;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author guilherme
 */
public class CervejaEstabelecimento extends Cerveja {
	int ceId;
	float preco;

	public int getCeId() {
		return ceId;
	}

	public void setCeId(int ceId) {
		this.ceId = ceId;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}
	
	public void save() throws ClassNotFoundException{
		CervejaDAO cdao = new CervejaDAO();
		cdao.insereNoEstabelecimento(this);
	}
	
	public void removeNoEst() throws ClassNotFoundException {
		CervejaDAO cdao = new CervejaDAO();
		cdao.removeNoEstabelecimento(this);
	}
	
	public JsonObject toJson(){
        JsonObject json = (JsonObject) Json.createObjectBuilder()
                    .add("id", getId())
                    .add("ce_id", getCeId())
                    .add("nome", getNome())
                    //.add("fabricante", getFabricante())
                    //.add("pais", getPais())
                    //.add("tipo", getTipo())
                    //.add("porcentagem", getPorcentagem())
                    .add("preco", getPreco())
                    .build();
		
        
        return json;
    }
}
