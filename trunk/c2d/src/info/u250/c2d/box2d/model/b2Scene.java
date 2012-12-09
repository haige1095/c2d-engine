package info.u250.c2d.box2d.model;

import java.util.ArrayList;
import java.util.List;

public class b2Scene implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	public List<b2FixtureDefModel> fixtureDefModels = new ArrayList<b2FixtureDefModel>();
	public List<b2BodyDefModel> bodyDefModels = new ArrayList<b2BodyDefModel>();
	public List<b2JointDefModel> jointDefModels = new ArrayList<b2JointDefModel>();
}
