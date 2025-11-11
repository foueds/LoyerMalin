# 🏠 LoyerMalin - Système de Cashback pour Loyers

Application Spring Boot permettant de gérer un système de cashback pour les paiements de loyers. Les locataires peuvent gagner du cashback en payant leur loyer à temps.

## 📋 Table des matières

- [Fonctionnalités](#-fonctionnalités)
- [Technologies](#-technologies)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Utilisation](#-utilisation)
- [API Endpoints](#-api-endpoints)
- [Structure du projet](#-structure-du-projet)
- [Base de données](#-base-de-données)
- [Sécurité](#-sécurité)
- [Tests](#-tests)
- [Contribuer](#-contribuer)

## ✨ Fonctionnalités

- 🎯 **Gestion des contrats de location** : Création et suivi des contrats entre propriétaires et locataires
- 💰 **Système de cashback** : Attribution automatique de cashback lors des paiements à temps
- 📊 **Suivi du solde** : Consultation du solde cashback en temps réel
- 🔒 **Sécurité** : Authentification et autorisation avec Spring Security
- 📝 **Validation des paiements** : Vérification de l'éligibilité au cashback selon la date de paiement
- 🗄️ **Migrations de base de données** : Gestion versionnée avec Liquibase

## 🛠 Technologies

- **Java 17**
- **Spring Boot 3.5.7**
    - Spring Data JPA
    - Spring Security
    - Spring Web
    - Spring Validation
- **Liquibase** - Migration de base de données
- **PostgreSQL** - Base de données production
- **H2** - Base de données développement/tests
- **Lombok** - Réduction du code boilerplate
- **Maven** - Gestion des dépendances

## 📦 Prérequis

- Java JDK 17 ou supérieur
- Maven 3.6+
- PostgreSQL 12+ (pour la production)
- Un IDE (IntelliJ IDEA recommandé)

## 🚀 Installation

### 1. Cloner le repository

# Lancer l'application (Liquibase s'exécutera automatiquement)
mvn spring-boot:run

# Vérifier le statut des migrations
mvn liquibase:status

# Générer un rollback SQL
mvn liquibase:rollback -Dliquibase.rollbackCount=1

# Netcher et réappliquer (ATTENTION: supprime les données)
mvn liquibase:dropAll
mvn spring-boot:run
