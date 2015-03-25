package fr.ippon.contest.puissance4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contient principalement les cordonnées de la pièce ainsi que les "lignes",
 * qui représentent les lignes de pièces dans lesquelles la pièce courante se
 * retrouve
 * 
 * @author Adrien
 *
 */
public class PieceData {
	private char joueur;
	private Integer x;
	private Integer y;
	/**
	 * en clef, la direction de la ligne en valeur, les pièce composant la ligne
	 */
	private HashMap<String, List<Piece>> lignes;

	public PieceData(Integer y, HashMap<String, List<Piece>> lignes) {
		this.y = y;
		this.lignes = lignes;
	}

	public char getJoueur() {
		return joueur;
	}

	public void setJoueur(char joueur) {
		this.joueur = joueur;
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

	public List<Piece> getLigne(String sens) {
		return lignes.get(sens);
	}

	public List<Integer> getLongueursLignes() {
		return lignes.entrySet().stream().map(x -> x.getValue().size())
				.collect(Collectors.toList());
	}

	public boolean possedeLigneDansLeSens(String sens) {
		return lignes.containsKey(sens);
	}

	public void ajouterLignesDansSens(String sens, ArrayList<Piece> pieces) {
		lignes.put(sens, pieces);

	}
}