// See https://docs.quarkiverse.io/quarkus-web-bundler/dev/advanced-guides.html#web-dependencies
// for more information about how to import web-dependencies:

// Example:
// in your pom.xml:
// <dependency>
// 	<groupId>org.mvnpm</groupId>
// 	<artifactId>jquery</artifactId>
// 	<version>3.7.1</version>
// 	<scope>provided</scope>
// </dependency>
//
// in this file:
// import $ from 'jquery'

// This app will be bundled by the Web-Bundler (including the imported libraries) and available using the {#bundle /} tag
// for more information about how to use the {#bundle /} tag, see https://docs.quarkiverse.io/quarkus-web-bundler/dev/advanced-guides.html#bundle-tag

import Alpine from 'alpinejs';

window.Alpine = Alpine;

Alpine.start();