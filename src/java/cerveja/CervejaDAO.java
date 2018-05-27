	/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cerveja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import FabricaConexoes.FabricaConexoes;
import auxiliar.CervejaBusca;
import auxiliar.CervejaEstabelecimento;
import auxiliar.CervejaResultado;
import java.util.ArrayList;

/**
 *
 * @author guilherme
 */
public class CervejaDAO {
    private final Connection con;
    
    public CervejaDAO() throws ClassNotFoundException{
        this.con = new FabricaConexoes().getConnection();
    }
    
    /**
     *
     * @param u
     */
    public void insere(Cerveja c){
        String cmd = "insert into cerveja " +
                " (nome,tipo,porcentagem,pais,fabricante) " +
                " values(?,?,?,?,?)";
        
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTipo());
            stmt.setFloat(3, c.getPorcentagem());
            stmt.setString(4, c.getPais());
            stmt.setString(5, c.getFabricante());

            stmt.execute();
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
    }
	
	public void insereNoEstabelecimento(CervejaEstabelecimento c){
        String cmd = "INSERT INTO cervejaEstabelecimento (estabelecimento_id, cerveja_id, preco) VALUES (?, " +
			"( " +
			"	SELECT id FROM cerveja " +
			"    WHERE nome = ? " +
			"), ?)";
        
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            stmt.setInt(1, c.getCeId());
            stmt.setString(2, c.getNome());
            stmt.setFloat(3, c.getPreco());
			
			System.out.println(stmt);
			
            stmt.execute();
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
    }
	
	public void removeNoEstabelecimento(CervejaEstabelecimento c){
        String cmd = "delete from cervejaEstabelecimento " +
			"where cerveja_id = ( " +
			"	SELECT id FROM cerveja " +
			"   WHERE nome = ?) " +
			"AND estabelecimento_id = ?";

        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getCeId());
            
            System.out.println(stmt);
            
            stmt.execute();
        }catch(SQLException er){
            throw new RuntimeException(er);
        }
    }
    
    /**
     *
     * @return 
     */
    public ArrayList<Cerveja> seleciona(){
        String cmd = "select * from cerveja"; /*+
                " where ?" +
                " = ?";*/
        ArrayList<Cerveja> cs = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            ResultSet rs = stmt.executeQuery();
              
            Cerveja c;
            while(rs.next()){
				
                c = new Cerveja();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTipo(rs.getString("tipo"));
                c.setPorcentagem(rs.getFloat("porcentagem"));
                c.setPais(rs.getString("pais"));
                c.setFabricante(rs.getString("fabricante"));
                
				//System.out.print(c.getNome());
				
                cs.add(c);
            }
                
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
        
        return cs;
    }
	
	public ArrayList<CervejaResultado> getCervejaResultado(CervejaBusca cb){
		ArrayList<CervejaEstabelecimento> cervs = new ArrayList<>();
		
		String cmd = "SELECT nome, nomeOficial AS estabelecimento, preco, latitude, longitude, e.id " +
			"FROM cervejaEstabelecimento ce, cerveja c, estabelecimento e " +
			"WHERE c.id = ce.cerveja_id " +
			"AND e.id = ce.estabelecimento_id " +
			"AND preco <= ? ";
		
		if(cb.getNome() != null)
			cmd += "AND c.nome = ?";
		
		
        ArrayList<CervejaResultado> cs = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            stmt.setFloat(1, cb.getPrecoMaximo());
			
			if(cb.getNome() != null)
				stmt.setString(2, cb.getNome());
			
			System.out.println(stmt);
			
			ResultSet rs = stmt.executeQuery();
			
            CervejaResultado c;
            while(rs.next()){
				
                c = new CervejaResultado();
                c.setNome(rs.getString("nome"));
                c.setEstabelecimento(rs.getString("estabelecimento"));
                c.setPreco(rs.getFloat("preco"));
                c.setLatitude(rs.getFloat("latitude"));
                c.setLongitude(rs.getFloat("longitude"));
				c.setEid(rs.getInt("id"));
				
                cs.add(c);
            }
                
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
		
		return cs;
	}

    /**
     *
     * @param c
     */
    public void atualiza(Cerveja c){
        String cmd = "update cerveja set " +
                "nome=?,tipo=?,porcentagem=?,pais=?,fabricante=? where id = ?";
        
        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTipo());
            stmt.setFloat(3, c.getPorcentagem());
            stmt.setString(4, c.getPais());
            stmt.setString(4, c.getFabricante());
            stmt.setInt(5, c.getId());
            
            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
    }

    /**
     *
     * @param id
     */
    public void remove(int id){
        String cmd = "delete from cerveja " +
                "where id = ?";
        
        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setInt(1, id);
            
            stmt.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
}