package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Arco;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void getVertex(Integer anno, Map<Integer, Director> idMap) {
		String sql = "SELECT DISTINCT d.id AS id, d.first_name AS first_name, d.last_name AS last_name "
				+ "FROM directors d, movies_directors md, movies m "
				+ "WHERE d.id = md.director_id AND md.movie_id = m.id AND m.year = ?";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				idMap.put(director.getId(), director);
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public List<Arco> getEdges(Integer anno, Map<Integer, Director> idMap) {
		String sql = "SELECT d1.id AS id1, d2.id AS id2, COUNT(*) AS peso "
				+ "FROM directors d1, directors d2, movies_directors md1, movies_directors md2, "
				+ "	movies m1, movies m2, roles r1, roles r2 "
				+ "WHERE d1.id = md1.director_id AND md1.movie_id = m1.id AND md1.movie_id = r1.movie_id "
				+ "	AND m1.year = ? AND d2.id = md2.director_id AND md2.movie_id = m2.id "
				+ "	AND md2.movie_id = r2.movie_id AND m2.year = ? AND "
				+ "	r1.actor_id = r2.actor_id AND d1.id < d2.id "
				+ "GROUP BY d1.id, d2.id";
		List<Arco> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Arco arco = new Arco(idMap.get(res.getInt("id1")), idMap.get(res.getInt("id2")), res.getInt("peso"));
				
				result.add(arco);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
}
