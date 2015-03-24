package fr.ippon.contest.puissance4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.pattern.LiteralPatternConverter;

public class Piece {
	private char joueur;
	private Integer x, y = -1;

	/*
	 * references vers les lignes horizontales, verticales , diagonales gauche
	 * et droite qui contiennent la piece
	 */
	private HashMap<String, List<Piece>> lignes = new HashMap<String, List<Piece>>();

	public Piece(int colonne, int ligne, char joueur) {
		x = colonne;
		y = ligne;
		this.joueur = joueur;
	}

	public boolean ajouter(String sens, Piece piece) {

		if (!lignes.containsKey(sens)) {
			ArrayList<Piece> nouvelleLigne = new ArrayList<Piece>();
			nouvelleLigne.add(piece);
			if (piece.getLigne(sens) != null)
				nouvelleLigne.addAll(piece.getLigne(sens));
			lignes.put(sens, (new ArrayList<Piece>(nouvelleLigne)));
			return true;
		} else {
			ArrayList<Piece> ligne = (ArrayList<Piece> )lignes.get(sens);
			List<Piece> clone = (List<Piece>)ligne.clone();
			clone.forEach(pieceDansLigne -> {
				if (pieceDansLigne.getLigne(sens) == null) {
					pieceDansLigne.ajouter(sens, piece);
				} else {
					pieceDansLigne.getLigne(sens).add(piece);
				}
			});
			return ligne.add(piece);
		}
	}

	public List<Integer> getLongueursLignes() {
		return lignes.entrySet().stream().map(x -> x.getValue().size())
				.collect(Collectors.toList());
	}

	public Integer GenereCleLigne(Entry<String, List<Piece>> in) {
		return in
				.getValue()
				.stream()
				.map(pieceDansListe -> (pieceDansListe.getX() * 10)
						+ pieceDansListe.getY())
				.reduce(new Integer(0), (a, b) -> a + b);
	}

	public String isAdjacent(Piece in) {
		if (in.getX().equals(this.getX())) {
			// sur la même ligne horizontal
			return "h";
		} else if (in.getY().equals(this.getY())) {
			// sur la même ligne vertical
			return "v";
		} else if ((in.getX() + 1 == this.getX() && in.getY() + 1 == this
				.getY())
				|| (in.getY() - 1 == this.getY() && in.getX() - 1 == this
						.getX())) {
			// diagonale gauche
			return "dg";
		} else if ((in.getX() + 1 == this.getX() && in.getY() - 1 == this
				.getY())
				|| (in.getY() + 1 == this.getY() && in.getX() - 1 == this
						.getX())) {
			// diagonale droite
			return "dd";
		} else {
			return null;
		}

	}

	public void displayLine() {
		System.out.println("display lignes ");
		lignes.entrySet().forEach(System.out::println);
	}

	public List<Integer> getCoordonneesAdjacents() {
		List<String> coordoneesAdj = new ArrayList<String>();

		coordoneesAdj.add("" + ((x - 1) * 10 + (y - 1)));
		coordoneesAdj.add("" + ((x - 1) * 10 + y));
		coordoneesAdj.add("" + ((x - 1) * 10 + (y + 1)));

		coordoneesAdj.add("" + ((x) * 10 + (y - 1)));
		coordoneesAdj.add("" + ((x) * 10 + (y + 1)));

		coordoneesAdj.add("" + ((x + 1) * 10 + (y - 1)));
		coordoneesAdj.add("" + ((x + 1) * 10 + y));
		coordoneesAdj.add("" + ((x + 1) * 10 + (y + 1)));

		return coordoneesAdj.stream().filter(x -> !x.contains("-"))
				.map(c -> new Integer(c)).collect(Collectors.toList());

	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Piece)
			return ((Piece) obj).getX().equals(this.getX())
					&& ((Piece) obj).getY().equals(this.getY());
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.getX() * 10 + this.getY();
	};

	@Override
	public String toString() {
		return "[" + this.joueur + " : (" + this.getX() + "," + this.getY()
				+ ") ]";
	};

	public Integer GenererValeurLigne(Entry<String, List<Piece>> in) {
		return in.getValue().size();
	}

	private Integer getPositionYDiagonaleGauche() {
		return this.getY() - 1;
	}

	private Integer getPositionYDiagonaleDroite() {
		return this.getY() + 1;
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

	public char getJoueur() {
		return joueur;
	}

	public void setJoueur(char joueur) {
		this.joueur = joueur;
	}

	public List<Piece> getLigne(String sens) {
		return this.lignes.get(sens);
	}
}
