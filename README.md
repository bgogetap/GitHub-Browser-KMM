# GitHub Browser - Multi-Module Kotlin Multiplatform Mobile Sample

This is a simple app that displays the top GitHub repositories (for Kotlin, currently), and allows
the user to browse details of a selected repository.

## Focus
The main focus of this repo is to demonstrate using multiple KMM modules (see the `xplat` directory),
rather than a single one.

In the end, the KMM modules do need to be bundled into a single "composite" module for the iOS
framework. I am not aware of any strategy to allow inter-compatible, separate KMM frameworks at this
time. This is due to the Kotlin types being unique and specific to each framework.