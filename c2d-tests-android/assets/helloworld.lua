--This is a 'Lua Global'
int = "Hello World from a Lua Global!"

--This is a function gl is the object we pushed(this.gl)
function render(gl)
--set the color of the screen
	gl:glClearColor(0, 0, 0, 1)
end

function fontBatch(font, batch)
--set the color for this font and draw it
	font:setColor(1, 1, 0, 1)
	font:draw(batch, "Hello World from a Lua Function!", 120, 200)
end



