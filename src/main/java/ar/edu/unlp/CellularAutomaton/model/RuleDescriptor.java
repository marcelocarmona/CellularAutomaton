package ar.edu.unlp.CellularAutomaton.model;

import ar.edu.unlp.CellularAutomaton.swing.RuleFrame;



/**
 * Rule selected in the RuleFrame
 * @see RuleFrame
 * @author mclo
 */
public enum RuleDescriptor {
	LIFE(	"Life", 
			"Conway's Game of Life", 
			Neighborhood.MOORE,
			new Rule(Neighborhood.MOORE.getSize(),"Survivals", 3), 
			new Rule(Neighborhood.MOORE.getSize(),"Births", 2,3)),
	HIGHLIFE("HighLife", 
			"Very similar to Conway's Life but with an interesting replicator", 
			Neighborhood.MOORE, 
			new Rule(Neighborhood.MOORE.getSize(),"Survivals", 3,6),
			new Rule(Neighborhood.MOORE.getSize(),"Births", 2,3)),
	DAY_AND_NIGHT(
			"Day & Night", 
			"Dead cells in a sea of live cells behave the same as live cells in a sea of dead cells", 
			Neighborhood.MOORE, 
			new Rule(Neighborhood.MOORE.getSize(),"Survivals", 3,6,7,8),
			new Rule(Neighborhood.MOORE.getSize(),"Births", 3,4,6,7,8)),
	DIAMOEBA("Diamoeba",
			"Create diamond-shaped blobs with unpredictable behavior", 
			Neighborhood.MOORE, 
			new Rule(Neighborhood.MOORE.getSize(),"Survivals", 3,5,6,7,8),
			new Rule(Neighborhood.MOORE.getSize(),"Births", 5,6,7,8)),
	SEEDS(
			"Seeds", 
			"Every living cell dies every generation, but most patterns still explode", 
			Neighborhood.MOORE, 
			new Rule(Neighborhood.MOORE.getSize(),"Survivals", 2),
			new Rule(Neighborhood.MOORE.getSize(),"Births")),
	PERSIAN_RUG(
			"Persian Rug", 
			"A single 2x2 block turns into a set of Persian rugs", 
			Neighborhood.MOORE, 
			new Rule(Neighborhood.MOORE.getSize(),"Survivals", 2,3,4),
			new Rule(Neighborhood.MOORE.getSize(),"Births")),
	LONGLIFE(
			"LongLife",
			"Oscillators with extremely long periods can occur quite naturally", 
			Neighborhood.MOORE, 
			new Rule(Neighborhood.MOORE.getSize(),"Survivals", 3,4,5),
			new Rule(Neighborhood.MOORE.getSize(),"Births", 5));
	
	private final String name;
	private final String description;
	private Neighborhood neighborhood;
	private Rule aliveRule;
	private Rule deadRule;
	
	/**
	 * @param name rule's name
	 * @param description rule's description
	 * @param deadRule to create an ArrayRule for dead state
	 * @param aliveRule to create an ArrayRule for dead alive
	 */
	private RuleDescriptor(String name, String description,Neighborhood neighborhood,Rule deadRule, Rule aliveRule) {
		this.name = name;
		this.description = description;
		this.neighborhood = neighborhood;
		this.aliveRule = aliveRule;
		this.deadRule = deadRule;
	}
	
    /**
	 * @return an Rule
	 */
	public Rule getAliveRule() {
		return aliveRule;
	}

	/**
	 * @return an Rule
	 */
	public Rule getDeadRule() {
		return deadRule;
	}

	/**
	 * @return rule's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return rule's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return A copy of Neighborhood
	 */
	public Neighborhood getNeighborhood() {
		return  new Neighborhood(neighborhood);
	}


	/* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString() {
		StringBuilder b = new StringBuilder();
		for (RuleValue ruleValue : aliveRule.getValues()) {
			b.append(ruleValue);
		}
		b.append("/");
		for (RuleValue ruleValue : deadRule.getValues()) {
			b.append(ruleValue);
		}
		b.append(" - ");
		b.append(name);
		b.append(" - ");
		b.append(description);
		return b.toString();
      }
}
