# 📌 Issue : Implémenter la Step 13 — Kubernetes Smoke Test on Minikube (CI)

## 🎯 Titre : 
`feat(ci): ajouter un test de fumée Kubernetes dans GitHub Actions (Step 13)`

## 📝 Description :
Mise en place d'un test automatisé dans notre CI pour déployer l'application sur un cluster Kubernetes en direct lors des tests d'intégration.

### ✅ Éléments ajoutés :
- **Actuator :** Ajout de `spring-boot-starter-actuator` pour exposer le point de santé (`/actuator/health`).
- **Profile CI (Stub) :** Création de `CiChatModelConfig` pour désactiver les appels vers les API d'IA externes pendant les tests.
- **Manifeste K8s :** Fichier `agent/src/main/k8s/ai-agent-deployment.yml` configuré pour le profile `ci`.
- **Pipeline CI (GitHub Actions) :** Ajout du job `kubernetes` exécutant un démarrage de Minikube et testant la réponse de l'application via Actuator.

> 🛡️ **Sécurité :** Grâce au Profile `ci`, aucun secret réel (comme `GEMINI_API_KEY`) n'est nécessaire ni exposé pendant les tests en CI.
