#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP 
#endif
varying vec2 v_tex0;
uniform sampler2D u_texture0;
uniform vec4 u_color;
void main() {
 if (u_color.r == 0.0 && u_color.g == 0.0 && u_color.b == 0.0 && u_color.a == 0.0 ) { 
 gl_FragColor = vec4(1, 0, 0, 0.5);
} else { 
 gl_FragColor = u_color; 
} gl_FragColor = gl_FragColor *  texture2D(u_texture0,  v_tex0);
}