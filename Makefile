run: App
	java App

App: App.java Panel.java
	javac $^

.PHONY: clean
clean:
	rm ./*.class


