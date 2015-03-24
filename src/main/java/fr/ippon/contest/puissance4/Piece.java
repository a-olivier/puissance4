package fr.ippon.contest.puissance4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Piece {
	private char joueur;
	private Integer x, y = -1;

	/*
	 * references vers les lignes horizontales, verticales , diagonales gauche et
	 * droite qui contiennent la piece
	 */
	private HashMap<String, List<Piece>> lignes = new HashMap<String, List<Piece>>();

	public Piece(int colonne, int ligne , char joueur) {
		x = colonne ; 
		y = ligne ; 
		this.joueur = joueur; 
	}

	public boolean ajouter(String sens, Piece piece) {
		List<Piece> ligne = lignes.get(sens);		
		ligne.forEach(pieceDansLigne -> pieceDansLigne.getLigne(sens).add(piece));
		return ligne.add(piece);
	}

	public Map<Integer, Integer> getLongueursLignesAvecId() {
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

	public String isAdjacent(Piece in)
	{
		if(in.getX().equals(this.getX())){
			//sur la même ligne horizontal
			return "h";
		}else if( in.getY().equals(this.getY())){
			// sur la même ligne vertical
			return "v";
		} else if(in.getPositionYDiagonaleGauche().equals(this.getY())){
			// diagonale gauche 
			return "dg";
		} else if (in.getPositionYDiagonaleGauche().equals(getY())){
			//diagonale droite 
			return "dd";
		} else {
			return null;
		}
	
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Piece)
			return ((Piece)obj).getX().equals(this.getX()) && ((Piece)obj).getY().equals(this.getY()) ; 
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return this.getX() * 10 + this.getY();
	};

	@Override
	public String toString() {
		return this.joueur + " : (" + this.getX() + "," + this.getY() + ")"; 
	};
	
	public Integer GenererValeurLigne(Entry<String, List<Piece>> in) {
		return in.getValue().size();
	}

	private Integer getPositionYDiagonaleGauche() {
		return this.getY() - 1 ;
	}
	
	private Integer getPositionYDiagonaleDroite() {
		return this.getY() + 1 ;
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
	
	public List<Piece> getLigne(String sens)
	{
		return this.lignes.get(sens);
	}
}
