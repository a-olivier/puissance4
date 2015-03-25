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

/**
 * Contient les méthodes en relation avec les listes de pièces adacentes
 * contenues dans data ( voir @see {@link PieceData} )
 * 
 * @author Adrien
 *
 */
public class Piece {
	private static final String DIAGO_DROITE = "dd";
	private static final String DIAGO_GAUCHE = "dg";
	private static final String VERTICAL = "v";
	private static final String HORIZONTAL = "h";
	// data holder
	private PieceData data = new PieceData(-1,
			new HashMap<String, List<Piece>>());

	public Piece(int colonne, int ligne, char joueur) {
		data.setX(colonne);
		data.setY(ligne);
		data.setJoueur(joueur);
	}

	public static int genererIdLigne(int colonne, int ligne) {
		return colonne * 10 + ligne;
	}

	public void ajouter(String sens, Piece piece) {

		if (!data.possedeLigneDansLeSens(sens)) {
			ArrayList<Piece> nouvelleLigne = new ArrayList<Piece>();
			nouvelleLigne.add(piece);

			if (piece.getData().getLigne(sens) != null)
				nouvelleLigne.addAll(piece.getData().getLigne(sens));
			data.ajouterLignesDansSens(sens, (new ArrayList<Piece>(
					nouvelleLigne)));
		} else {
			ArrayList<Piece> ligne = (ArrayList<Piece>) data.getLigne(sens);
			List<Piece> clone = (List<Piece>) ligne.clone();
			clone.forEach(pieceDansLigne -> {
				if (pieceDansLigne.getData().getLigne(sens) == null) {
					pieceDansLigne.ajouter(sens, piece);
				} else {
					pieceDansLigne.getData().getLigne(sens).add(piece);
				}
			});
		}
	}

	public List<Integer> getLongueursLignes() {
		return data.getLongueursLignes();
	}

	public String getDirectionAdj(Piece in) {
		if (in.getData().getX().equals(data.getX())) {
			// sur la même ligne horizontal
			return HORIZONTAL;
		} else if (in.getData().getY().equals(data.getY())) {
			// sur la même ligne vertical
			return VERTICAL;
		} else if ((in.getData().getX() + 1 == data.getX() && in.getData()
				.getY() + 1 == this.getData().getY())
				|| (in.getData().getY() - 1 == data.getY() && in.getData()
						.getX() - 1 == this.getData().getX())) {
			// diagonale gauche
			return DIAGO_GAUCHE;
		} else if ((in.getData().getX() + 1 == data.getX() && in.getData()
				.getY() - 1 == this.getData().getY())
				|| (in.getData().getY() + 1 == data.getY() && in.getData()
						.getX() - 1 == this.getData().getX())) {
			// diagonale droite
			return DIAGO_DROITE;
		} else {
			return null;
		}

	}

	public List<Integer> getCoordonneesAdjacents() {
		List<String> coordoneesAdj = new ArrayList<String>();

		coordoneesAdj.add(""
				+ genererIdLigne((data.getX() - 1), data.getY() - 1));
		coordoneesAdj.add("" + genererIdLigne((data.getX() - 1), data.getY()));
		coordoneesAdj.add(""
				+ genererIdLigne((data.getX() - 1), (data.getY() + 1)));

		coordoneesAdj
				.add("" + genererIdLigne((data.getX()), (data.getY() - 1)));
		coordoneesAdj
				.add("" + genererIdLigne((data.getX()), (data.getY() + 1)));

		coordoneesAdj.add(""
				+ genererIdLigne((data.getX() + 1), (data.getY() - 1)));
		coordoneesAdj.add("" + genererIdLigne((data.getX() + 1), data.getY()));
		coordoneesAdj.add(""
				+ genererIdLigne((data.getX() + 1), (data.getY() + 1)));

		return coordoneesAdj.stream().filter(x -> !x.contains("-"))
				.map(c -> new Integer(c)).collect(Collectors.toList());

	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Piece) {
			return ((Piece) obj).getData().getX().equals(data.getX())
					&& ((Piece) obj).getData().getY().equals(data.getY());
		} else {
			return super.equals(obj);
		}
	}

	public PieceData getData() {
		return data;
	}

	@Override
	public int hashCode() {
		return genererIdLigne(data.getX(), data.getY());
	};

	@Override
	public String toString() {
		return "[" + this.data.getJoueur() + " : (" + data.getX() + ","
				+ data.getY() + ") ]";
	};

	public Integer GenererValeurLigne(Entry<String, List<Piece>> in) {
		return in.getValue().size();
	}

}
