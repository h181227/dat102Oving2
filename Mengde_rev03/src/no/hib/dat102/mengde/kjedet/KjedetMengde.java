package no.hib.dat102.mengde.kjedet;

import no.hib.dat102.mengde.adt.*;
//********************************************************************
// Kjedet implementasjon av en mengde. 
//********************************************************************
import java.util.*;

public class KjedetMengde<T> implements MengdeADT<T> {
 private static Random rand = new Random();
 private int antall; // antall elementer i mengden
 private LinearNode<T> start;

 /**
  * Oppretter en tom mengde.
  */
 public KjedetMengde() {
  antall = 0;
  start = null;
 }//

  
 @Override
 public void leggTil(T element) {
  if (!(inneholder(element))) {
   LinearNode<T> node = new LinearNode<T>(element);
   node.setNeste(start);
   start = node;
   antall++;
  }
 }


public void leggTilAlle(MengdeADT<T> m2) {
  Iterator<T> teller = m2.oppramser();
  while (teller.hasNext()){
   leggTil(teller.next());
  }
 }

 
 @Override
 public T fjernTilfeldig() {
  LinearNode<T> forgjenger, aktuell;
  T resultat = null;
  if (!erTom()) {
   int valg = rand.nextInt(antall) + 1;
   if (valg == 1) {
    resultat = start.getElement();
    start = start.getNeste();
   } else {
    forgjenger = start;
    for (int nr = 2; nr < valg; nr++){
     forgjenger = forgjenger.getNeste();
    }
    aktuell = forgjenger.getNeste();
    resultat = aktuell.getElement();
    forgjenger.setNeste(aktuell.getNeste());
   }
   antall--;
  }// if
  return resultat;
 }//

 
	@Override
	public T fjern(T element) {
		LinearNode<T> forgjenger, aktuell = null;
		aktuell = start;
		T resultat = null;
		forgjenger = null;
		
		for (int sok = 0; sok < antall; sok++) {
			if (aktuell.getElement().equals(element)) {
				if (forgjenger.equals(null)) {
					start = start.getNeste();
					resultat = start.getElement();
				} else {
					forgjenger.setNeste(aktuell.getNeste());
					resultat = aktuell.getElement();
				}
				antall--;
			}
			forgjenger = aktuell;
			aktuell = aktuell.getNeste();
		}
		return resultat;
	}//

 @Override
 public MengdeADT<T> union(MengdeADT<T> m2) {// OBS! En bedre i kladdeopg4
  MengdeADT<T> begge = new KjedetMengde<T>();
  LinearNode<T> aktuell = start;
  while (aktuell != null) {
   begge.leggTil(aktuell.getElement());
   aktuell = aktuell.getNeste();
  }// while
  Iterator<T> teller = m2.oppramser();
  while (teller.hasNext()){
     begge.leggTil(teller.next());
  }
  return begge;
 }//

   
 private void settInn(T element){
 LinearNode<T>nyNode = new LinearNode<T>(element);
 nyNode.setNeste(start);
 start = nyNode; 
 antall++;
 }
  
 
 @Override
 public boolean inneholder(T element) {
  boolean funnet = false;
  LinearNode<T> aktuell = start;
  for (int sok = 0; sok < antall && !funnet; sok++) {
   if (aktuell.getElement().equals(element)){
     funnet = true;
	}
   else{
    aktuell = aktuell.getNeste();
	}
  }
  return funnet;
 }

 
 @Override
 public boolean erLik(MengdeADT<T> m2) {
  boolean likeMengder = true;
  //likeMengder = antall == m2.antall();
  LinearNode<T> aktuell = start;
  for (int i=0;i<antall && likeMengder;i++) {
	  likeMengder = m2.inneholder(aktuell.getElement());
	  aktuell = aktuell.getNeste();
  }
  return likeMengder;
 }

 
 @Override
 public boolean erTom() {
  return antall == 0;
 }

 
 @Override
 public int antall() {
  return antall;
 }

 @Override
 public Iterator<T> oppramser() {
  return new KjedetIterator<T>(start);
 }

}// class
