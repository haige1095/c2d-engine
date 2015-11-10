# Box2d Soft body Use Mesh

# Introduction #

As the description at [wikipedia](http://en.wikipedia.org/wiki/Soft_body_dynamics).Soft body dynamics is a field of computer graphics that focuses on visually realistic physical simulations of the motion and properties of deformable objects (or soft bodies).

This example use the second method : Mass-spring models .

![http://upload.wikimedia.org/wikipedia/commons/thumb/6/62/Two_nodes_as_mass_points_connected_by_parallel_circuit_of_spring_and_damper.svg/220px-Two_nodes_as_mass_points_connected_by_parallel_circuit_of_spring_and_damper.svg.png](http://upload.wikimedia.org/wikipedia/commons/thumb/6/62/Two_nodes_as_mass_points_connected_by_parallel_circuit_of_spring_and_damper.svg/220px-Two_nodes_as_mass_points_connected_by_parallel_circuit_of_spring_and_damper.svg.png)

# How to achieve that #
## create spring bodys ##
We use the **Distance Joint**.

A distance joint keeps the distance between two points a constant. A distance joint can be made soft to simulate a springy connection. We will be creating a wheel like structure in Box2d and then connecting all of the bodies with a springy distance joint in order to simulate the squishiness.

We will create a wheel like shape use NUM\_SEGMENTS circles. And join them together. The result is :
![http://c2d-engine.googlecode.com/svn/wiki/img/box2d-soft-body-outline.png](http://c2d-engine.googlecode.com/svn/wiki/img/box2d-soft-body-outline.png)
```
final int NUM_SEGMENTS = 16;
final float SPRING = 10.0f;
```
Here is the code , it use the c2d-engine body defined method.
```
Cb2World.getInstance().installDefaultWorld();
final PhysicalFingerInput input = new PhysicalFingerInput(Cb2World.getInstance().createScreenBox());

final CircleData center = new CircleData();
final Array<CircleData> circles = new Array<CircleData>();
final Array<DistanceJointData> joints = new Array<DistanceJointData>();
center.radius = 10;
center.density = 0.1f;
center.restitution = 0.05f;
center.friction = 1;
center.center.set(300,200);
center.isDynamic = true;
center.build();
for(int i=0;i<NUM_SEGMENTS;i++){
	float x = MathUtils.cosDeg(360/NUM_SEGMENTS*i)*50;
	float y = MathUtils.sinDeg(360/NUM_SEGMENTS*i)*50;
	CircleData subCircleData = new CircleData();
	subCircleData.radius = 10;
	subCircleData.density = 0.1f;
	subCircleData.restitution = 0.05f;
	subCircleData.friction = 1;				
	subCircleData.center.set(center.center).add(x,y);
	subCircleData.isDynamic = true;
	subCircleData.build();
	circles.add(subCircleData);
}
for (int i = 0; i < NUM_SEGMENTS; i++) {
    int neighborIndex = (i + 1) % NUM_SEGMENTS;
    DistanceJointData joint1 = new DistanceJointData();
	joint1.bodyA = center	;
	joint1.bodyB = circles.get(i);
	joint1.collideConnected = false;
	joint1.frequencyHz = SPRING;
	joint1.dampingRatio = 0.1f;
	joint1.build();
	joints.add(joint1);
	
	DistanceJointData joint2 = new DistanceJointData();
	joint2.bodyA = circles.get(neighborIndex);
	joint2.bodyB = circles.get(i);
	joint2.collideConnected = false;
	joint2.frequencyHz = SPRING;
	joint2.dampingRatio = 0.1f;
	joint2.build();
	joints.add(joint2);
}
```
### Let it jump when we click the screen ###
```
private void bounce(){
Vector2 impulse = new Vector2( center.body.getMass()*0,center.body.getMass()*150);
Vector2 impulsePoint = center.body.getPosition();
center.body.applyLinearImpulse(impulse,impulsePoint);
}
```
## The Mesh to bind image ##
The sprite or the TextureRegion is not support complex texture bind. So we use the libgdx's Mesh. It has the same result with opengles but it's more convenient to use . In this scene . we need defined the texture's positon and it's texture coordinates and it's color.Texture coordinates must in the range of 0 to 1, with the origin at (0, 0).

The mesh can be declare like this .
```
final Mesh mesh = new Mesh(false, NUM_SEGMENTS+1+1, NUM_SEGMENTS+1+1, 
new VertexAttribute(Usage.Position, 3, "a_position"),
new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"),
new VertexAttribute(Usage.Color, 4, "a_color"));
```
We have **NUM\_SEGMENTS+1+1** to enclose it (include the center one). So the indices:
```
final short[] indices = new short[NUM_SEGMENTS+1+1];
  for (int i = 0; i < NUM_SEGMENTS+1+1; i++) {
  indices[i] = (short) i;
}
```
## Render ##
It's time to tell the engine how to draw the texture. Every frame we finger the new result of the texture's positon and the texture coordinates .We draw the circle polygon  with triangle fan.As menthioned that the texture map coordinates must be in the range of 0 to 1. We use the cosine and sine functions to calculate the x and y coordinates of each segment. And finally we add the color values.
let's Code:
```
vertices[0] = center.body.getPosition().x*Cb2World.RADIO;
vertices[1] = center.body.getPosition().y*Cb2World.RADIO;
vertices[2] = 0;
vertices[3] = 0.5f;
vertices[4] = 0.5f;
vertices[5] = 1;
vertices[6] = 1f;
vertices[7] = 1f;
vertices[8] = 1f;
for(int i=0;i<NUM_SEGMENTS;i++){
	vertices[(i+1)*9+0] = circles.get(i).body.getPosition().x*Cb2World.RADIO ;
	vertices[(i+1)*9+1] = circles.get(i).body.getPosition().y*Cb2World.RADIO;
	vertices[(i+1)*9+2] = 0 ;
	vertices[(i+1)*9+3] = MathUtils.cosDeg(360/NUM_SEGMENTS*i)*0.5f+0.5f ;
	vertices[(i+1)*9+4] = MathUtils.sinDeg(360/NUM_SEGMENTS*i)*0.5f+0.5f ;
	
	vertices[(i+1)*9+5] = 1 ;
	vertices[(i+1)*9+6] = 1 ;
	vertices[(i+1)*9+7] = 1 ;
	vertices[(i+1)*9+8] = 1 ;
}
vertices[(NUM_SEGMENTS+1)*9+0] = vertices[9+0];
vertices[(NUM_SEGMENTS+1)*9+1] = vertices[9+1];
vertices[(NUM_SEGMENTS+1)*9+2] = vertices[9+2];
vertices[(NUM_SEGMENTS+1)*9+3] = vertices[9+3];
vertices[(NUM_SEGMENTS+1)*9+4] = vertices[9+4];
vertices[(NUM_SEGMENTS+1)*9+5] = vertices[9+5];
vertices[(NUM_SEGMENTS+1)*9+6] = vertices[9+5];
vertices[(NUM_SEGMENTS+1)*9+7] = vertices[9+7];
vertices[(NUM_SEGMENTS+1)*9+8] = vertices[9+8];
mesh.setVertices(vertices);
```
### simple draw it ###
```
Gdx.graphics.getGL10().glEnable(GL10.GL_BLEND);
Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
texture.bind();			
mesh.render(GL10.GL_TRIANGLE_FAN);
Gdx.graphics.getGL10().glDisable(GL10.GL_TEXTURE_2D);
Gdx.graphics.getGL10().glDisable(GL10.GL_BLEND);
```
### Want debug ? ###
```
private void debug(){
	center.debug(Engine.getShapeRenderer());
	for(CircleData c:circles){
		c.debug(Engine.getShapeRenderer());
	}
	for(DistanceJointData j:joints){
		j.debug(Engine.getShapeRenderer());
	}
}
```

The full code can be found here:
https://code.google.com/p/c2d-engine/source/browse/trunk/c2d-tests/src/info/u250/c2d/tests/Box2d_SoftBodyTest.java