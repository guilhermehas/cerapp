/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estabelecimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import FabricaConexoes.FabricaConexoes;
import auxiliar.CervejaEstabelecimento;
import java.util.ArrayList;

/**
 *
 * @author guilherme
 */
public class EstabelecimentoDAO {
    private final Connection con;
    
    public EstabelecimentoDAO() throws ClassNotFoundException{
        this.con = new FabricaConexoes().getConnection();
    }
    
    /**
     *
     * @param e
     */
    public void insere(Estabelecimento e){
        String cmd = "insert into estabelecimento" +
                " (nomeOficial,nomeComercial,CNPJ,email,senha,latitude,longitude)" +
                " values(?,?,?,?,?,?,?)";
        
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            stmt.setString(1, e.getNomeOficial());
            stmt.setString(2, e.getNomeComercial());
            stmt.setString(3, e.getCNPJ());
            stmt.setString(4, e.getEmail());
            stmt.setString(5, e.getSenha());
            stmt.setFloat(6, e.getLatitude());
            stmt.setFloat(7, e.getLongitude());

            stmt.execute();
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
    }
	
	public boolean existeUsuario(Estabelecimento e){
        String cmd = "select count(*) from estabelecimento" +
                " where email=? AND senha=?";
        
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            
            stmt.setString(1, e.getEmail());
            stmt.setString(2, e.getSenha());
            
            ResultSet rs = stmt.executeQuery();
            
             if(rs.next())
				 return true;
                
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
        
        return false;
    }
    
    /**
     *
     * @param e
     * @return 
     */
    public Estabelecimento selecionaPorEmail(Estabelecimento e){
        String cmd = "select * from estabelecimento" +
                " where email=? AND senha=?";
        
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            
            stmt.setString(1, e.getEmail());
            stmt.setString(2, e.getSenha());
            
            ResultSet rs = stmt.executeQuery();
            
             while(rs.next()){
				e.setId(rs.getInt("id"));
                e.setCNPJ(rs.getString("CNPJ"));
                e.setNomeOficial(rs.getString("nomeOficial"));
                e.setNomeComercial(rs.getString("nomeComercial"));
                e.setLatitude(rs.getFloat("latitude"));
                e.setLongitude(rs.getFloat("longitude"));
            }
                
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
        
        return e;
    }
	
    
    public Estabelecimento selecionaPorId(Estabelecimento e){
        String cmd = "select * from estabelecimento" +
                " where id=?";
        
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            
            stmt.setInt(1, e.getId());
            
            ResultSet rs = stmt.executeQuery();
            
             while(rs.next()){
				e.setEmail(rs.getString("email"));
                e.setCNPJ(rs.getString("CNPJ"));
                e.setNomeOficial(rs.getString("nomeOficial"));
                e.setNomeComercial(rs.getString("nomeComercial"));
                e.setLatitude(rs.getFloat("latitude"));
                e.setLongitude(rs.getFloat("longitude"));
            }
                
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
        
        return e;
    }
    
    public ArrayList<CervejaEstabelecimento> selecionaCerveja(Estabelecimento e){
        String cmd = "SELECT ce.id AS ce_id, c.id, c.nome, c.fabricante, c.tipo, c.porcentagem, c.pais, ce.preco " +
			"FROM cervejaEstabelecimento ce, cerveja c " +
			"WHERE ce.estabelecimento_id = ? " +
			"AND ce.cerveja_id = c.id";
        
        ArrayList<CervejaEstabelecimento> cervs = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(cmd)) {
            
            stmt.setInt(1,e.getId());
            
            ResultSet rs = stmt.executeQuery();
              
            while(rs.next()){
                CervejaEstabelecimento c = new CervejaEstabelecimento();
                c.setId(rs.getInt("id"));
                c.setCeId(rs.getInt("ce_id"));
                c.setNome(rs.getString("nome"));
                c.setFabricante(rs.getString("fabricante"));
                c.setPais(rs.getString("pais"));
                c.setTipo(rs.getString("tipo"));
                c.setPorcentagem(rs.getFloat("porcentagem"));
                c.setPreco(rs.getFloat("preco"));
                cervs.add(c);
            }
                
        } catch (SQLException ex) {
        throw new RuntimeException(ex);
        }
        
        return cervs;
    }

    /**
     *
	 * @param e
     */
    public void atualiza(Estabelecimento e){
        String cmd = "update estabelecimento set " +
                "nomeOficial=?,nomeComercial=?,CNPJ=?,email=? where id = ?";
        
        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setString(1, e.getNomeOficial());
            stmt.setString(2, e.getNomeComercial());
            stmt.setString(3, e.getCNPJ());
            stmt.setString(4, e.getEmail());
            stmt.setInt(5, e.getId());
            
            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
    }
	
	/**
	 *
	 * @param e
	 */
	public void atualizaSenha(Estabelecimento e){
        String cmd = "update estabelecimento set " +
                "senha=? where id = ?";
        
        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setString(1, e.getSenha());
            stmt.setInt(2, e.getId());
            
            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
    }
	
	
	
    
    public void atualizaCerveja(int e, int c, float preco){
        String cmd = "update cervejaEstabelecimento set " +
                "preco=? where estabelecimento_id = ? AND cerveja_id = ?";
        
        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setFloat(1, preco);
            stmt.setInt(2, e);
            stmt.setInt(3, c);

            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
    }
    
    public void atualizaItemDiverso(int id, String nome, float preco, String descricao){
        String cmd = "update itemDiversoEstabelecimento set " +
                "nome=?, preco=?, descricao=? where id = ?";
        
        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setString(1, nome);
            stmt.setFloat(2, preco);
            stmt.setString(3, descricao);
            stmt.setInt(4, id);

            stmt.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
    }

    /**
     *
	 * @param e
     */
    public void delete(Estabelecimento e){
        String cmd = "delete from cervejaEstabelecimento "
            + "where estabelecimento_id = ?; ";
        
        
        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setInt(1, e.getId());
            stmt.execute();
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        
        cmd = "delete from estabelecimento "
            +    "where id = ?; ";
        
        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setInt(1, e.getId());
            
            stmt.execute();
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    /*
    public void removeCerveja(CervejaEstabelecimento c){
        String cmd = "delete from cervejaEstabelecimento " +
			"where cerveja_id = ( " +
			"	SELECT id FROM cerveja " +
			"   WHERE nome = '?') " +
			"AND estabelecimento_id = ?";

        try(PreparedStatement stmt = con.prepareStatement(cmd)){
            
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getCeId());
            
            //System.out.printf("cid = %d eid = %d\n", c, e);
            
            stmt.execute();
        }catch(SQLException er){
            throw new RuntimeException(er);
        }
    }*/

    public void insereCerveja(int id, String text, float preco, String text0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
