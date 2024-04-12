package classes;

import java.sql.SQLException;

public interface CRUD <T> {
	int incluir(T a) throws SQLException;
    void alterar();
    void excluir();
    void imprimir();
    void localizar();
}
