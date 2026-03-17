# 📌 Issue : Implémenter la Step 12 — Deploy on Minikube

## 🎯 Titre : 
`feat(k8s): ajouter les manifests Kubernetes pour le déploiement Minikube (Step 12)`

## 📝 Description :
Ajout des fichiers de configuration Kubernetes pour permettre le déploiement local de l'application sur Minikube, conformément à la section "Step 12" du README.

### ✅ Éléments ajoutés :
- `k8s/github-mcp.yaml` : Déploiement et Service pour le cluster-ip du serveur MCP GitHub.
- `k8s/ai-agent.yaml` : Déploiement et Service pour l'application Spring Boot AI Agent avec variables d'environnement adaptées.

> 🛠️ **Note :** Le déploiement nécessite un secret Kubernetes `agent-secrets` contenant les clés `ANTHROPIC_API_KEY` et `GITHUB_TOKEN`.
