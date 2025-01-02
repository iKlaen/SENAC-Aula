
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;



public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    //Ajustes no metodo para inserir os produtos no banco de dados utilizando o SQL
    public void cadastrarProduto(ProdutosDTO produto) {
    String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

    try {
        conn = new conectaDAO().connectDB(); // Conecta ao banco de dados
        prep = conn.prepareStatement(sql);
        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor());
        prep.setString(3, produto.getStatus());
        prep.executeUpdate();

        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    public ArrayList<ProdutosDTO> listarProdutos(){
    String sql = "SELECT * FROM produtos";
    listagem.clear();
    
    try {
        conn = new conectaDAO().connectDB(); // Conecta ao banco de dados
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            listagem.add(produto);
        }
    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
    } finally {
        try {
            if (resultset != null) resultset.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return listagem;
}
    public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";
    
    try {
        conn = new conectaDAO().connectDB(); // Conecta ao banco de dados
        prep = conn.prepareStatement(sql);
        prep.setString(1, "Vendido");
        prep.setInt(2, id);
        int rowsAffected = prep.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
        }
    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + erro.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}