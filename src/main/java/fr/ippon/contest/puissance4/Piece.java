package fr.ippon.contest.puissance4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.ippon.contest.puissance4.Puissance4.CouleurJoueur;

public class Piece {
	private CouleurJoueur joueur;
	private Integer x, y = -1;

	/*
	 * references vers les lignes horizontale, vertical , diagonale gauche et
	 * droite qui contiennent la piece
	 */
	private HashMap<String, List<Piece>> lignes = new HashMap<String, List<Piece>>();

	public boolean ajouter(String sens, Piece piece) {
		return lignes.get(sens).add(piece);
	}

	public Map<Integer, Integer> getLongueursLignes() {
		return (Map<Integer, Integer>) lignes
				.entrySet()
				.stream()
				.collect(
						Collectors.toMap(this::GenereCleLigne,
								this::GenererValeurLigne));
	}


	public Integer GenereCleLigne(Entry<String, List<Piece>> in) {
		return in
				.getValue()
				.stream()
				.map(pieceDansListe -> (pieceDansListe.getX() * 10)
						+ pieceDansListe.getY())
				.reduce(new Integer(0), (a, b) -> a + b);
	}

	public Integer GenererValeurLigne(Entry<String, List<Piece>> in) {
		return in.getValue().size();
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public CouleurJoueur getJoueur() {
		return joueur;
	}

	public void setJoueur(CouleurJoueur joueur) {
		this.joueur = joueur;
	}
}
