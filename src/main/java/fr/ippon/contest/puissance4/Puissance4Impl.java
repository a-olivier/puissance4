package fr.ippon.contest.puissance4;

import java.util.ArrayList;
import java.util.List;

public class Puissance4Impl implements Puissance4 {

	private CouleurJoueur joueurCourant;
	private List<Piece> LigneEnCours = new ArrayList<Piece>();

	@Override
	public void nouveauJeu() {
		joueurCourant = (Math.random() % 2 == 1 ? CouleurJoueur.JAUNE
				: CouleurJoueur.ROUGE);
		LigneEnCours = new ArrayList<Piece>();

	}

	@Override
	public void chargerJeu(char[][] grille, char tour) {
		// TODO Auto-generated method stub

	}

	@Override
	public EtatJeu getEtatJeu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char getTour() {
		return getCharJoueur(this.joueurCourant);
	}

	private char getCharJoueur(CouleurJoueur joueurEnum) {
		switch (joueurEnum) {
		case JAUNE:
			return 'J';
		case ROUGE:
			return 'R';
		}
		return ' ';
	}
	

	@Override
	public char getOccupant(int ligne, int colonne) {
		CouleurJoueur joueur = this.LigneEnCours.get(ligne * 10 + colonne).getJoueur();
		return getCharJoueur(joueur);
	}

	@Override
	public void jouer(int colonne) {
		// TODO Auto-generated method stub

	}

	private boolean isColonneValide(int colonne) {
		return (colonne < Puissance4.MAX_COLONNE && colonne > Puissance4.MIN_COLONNE);
	}

}
