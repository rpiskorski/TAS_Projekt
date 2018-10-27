# Tas - informacje

To README zostało napisane w celu rozwiania wątpliwości odnośnie stawiania Angulara z tego repozytorium.

## Instalacja Angulara

[Na komputerach wydziałowych](#Stawianie-projektu-na-komputerach-wydziałowych)

Do zainstalowania samego Angulara wykorzystuje się program

`npm install -g @angular/cli`

Dzięki temu będzie globalnie dostępny program `ng`

Oczywiście, by mieć npm należy zainstalować [Node.js](https://nodejs.org/en/)


## Stawianie projektu

`git clone https://github.com/Varmen8/TAS_Projekt`

I zmieniamy gałąź na frontendową (aktualnie `frontend-features`).

W repo nie znajdują się `node_modules`: moduły, które Node i spółka pozyskuje z `package.json` projektu. Wchodzimy przez terminal do naszego repo i wpisujemy lub kopiujemy:

`npm install`

Jeśli wszystko się powiodło to "server" można uruchomić poleceniem:

`ng serve --open`

lub

`ng s --open`

jeśli komuś szkoda czasu.

## Stawianie projektu na komputerach wydziałowych

`git clone https://github.com/Varmen8/TAS_Projekt`

Wchodzimy przez terminal do repo i

`npm install`

`npm install @angular/cli`

`npm run-script ng serve`

Uruchamianie Angulara może trochę potrwać (~13s)

# Tas - dodatkowe, wygenerowane info

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 6.2.4.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
