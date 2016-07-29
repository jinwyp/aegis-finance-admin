include ../aegis-docker/bin/Makefile

static:
	@cd frontend && npm install && bower install && gulp build