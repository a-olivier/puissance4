package fr.ippon.contest.puissance4;

public class Puissance4Impl implements Puissance4 {

	@Override
	public void nouveauJeu() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char getOccupant(int ligne, int colonne) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void jouer(int colonne) {
		// TODO Auto-generated method stub

	}
	
	private boolean isColonneValide(int colonne){
		return (colonne < Puissance4.MAX_COLONNE && colonne > Puissance4.MIN_COLONNE) ; 
	}

}
