package classes;

import java.sql.SQLException;

public interface CRUD {
	void incluir(Object a) throws SQLException;
    void alterar();
    void excluir();
    void imprimir();
    void localizar();
}
