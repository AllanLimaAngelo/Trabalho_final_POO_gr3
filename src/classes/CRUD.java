package classes;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUD <T> {
	int incluir(T a) throws SQLException;
    void alterar(T a);
    void excluir();
    void imprimir();
    void localizar();
	void excluir(int i);
}
