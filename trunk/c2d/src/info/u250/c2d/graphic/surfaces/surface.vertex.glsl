#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP 
#endif
attribute vec4 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projectionViewMatrix;
varying vec2 v_tex0;
void main() {
   gl_Position = u_projectionViewMatrix * a_position;
   v_tex0 = a_texCoord0;
}