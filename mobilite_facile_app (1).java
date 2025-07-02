<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Mobilité Facile</title>
  <style>
    body {
      font-family: 'Segoe UI', sans-serif;
      background: #f4f4f4;
      color: #333;
      padding: 2rem;
      margin: 0;
    }
    .container {
      max-width: 1100px;
      margin: auto;
      background: #fff;
      padding: 2rem;
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    h2 {
      color: #e67200;
      margin-top: 0;
      font-size: 1.5rem;
      border-bottom: 2px solid #eee;
      padding-bottom: 0.5rem;
    }
    .input-group {
      margin-bottom: 1rem;
    }
    label {
      font-weight: 600;
      margin-left: 0.5rem;
    }
    .radio-group {
      display: flex;
      flex-wrap: wrap;
      gap: 0.5rem;
      margin-top: 0.5rem;
    }
    .radio-group button {
      padding: 0.5rem 1rem;
      border: 2px solid #ccc;
      border-radius: 8px;
      background: #fafafa;
      cursor: pointer;
      transition: all 0.2s;
    }
    .radio-group button.active {
      background: #e67200;
      color: white;
      border-color: #e67200;
    }
    .actions {
      text-align: center;
      margin-top: 2rem;
    }
    .btn-primary {
      padding: 0.75rem 2rem;
      font-size: 1.1rem;
      background: #e67200;
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
    }
    header img {
      max-width: 300px;
      height: auto;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.15);
      opacity: 0;
      transform: translateY(-20px);
      transition: opacity 1s ease, transform 1s ease;
    }
    header img.show {
      opacity: 1;
      transform: translateY(0);
    }
    header {
      text-align: center;
      background: #fff;
      padding: 2rem;
      border-radius: 12px;
      margin-bottom: 2rem;
      box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    }
  </style>
</head>
<body>
  <header>
    <img id="headerImage" src="https://cdn.pixabay.com/photo/2021/04/08/14/36/disability-6161863_1280.png" alt="Accessibilité"/>
    <h1 style="color: #e67200;">Mobilité Facile</h1>
    <button id="playTutorialAudio" class="btn-primary" style="background: #4a90e2; margin-top: 1rem;">
      🎧 Écouter le tutoriel
    </button>
  </header>

  <div class="container">
    <h2>Options d’accessibilité</h2>

    <div class="input-group">
      <input type="checkbox" id="jumpToContent" />
      <label for="jumpToContent">Accès direct au contenu</label>
    </div>
    <div class="input-group">
      <input type="checkbox" id="remoteMode" />
      <label for="remoteMode">Mode télécommande</label>
    </div>
    <div class="input-group">
      <input type="checkbox" id="autoMode" />
      <label for="autoMode">Mode automatique</label>
    </div>

    <div class="input-group">
      <label>Délai de clic :</label>
      <div class="radio-group" id="clickDelay">
        <button data-value="2s">2s</button>
        <button data-value="4s">4s</button>
        <button data-value="6s">6s</button>
      </div>
    </div>

    <div class="input-group">
      <label>Position du menu :</label>
      <div class="radio-group" id="menuPosition">
        <button data-value="gauche">Gauche</button>
        <button data-value="proche">Proche</button>
        <button data-value="droite">Droite</button>
      </div>
    </div>

    <div class="input-group">
      <label>Délai automatique :</label>
      <div class="radio-group" id="autoDelay">
        <button data-value="2s">2s</button>
        <button data-value="5s">5s</button>
        <button data-value="10s">10s</button>
      </div>
    </div>

    <div class="input-group">
      <label>Navigation rapide :</label>
      <div class="radio-group" id="quickNav">
        <button data-value="5">5</button>
        <button data-value="10">10</button>
        <button data-value="15">15</button>
      </div>
    </div>

    <div class="actions">
      <button id="submitBtn" class="btn-primary">Appliquer les paramètres</button>
    </div>
  </div>

  <script>
    window.addEventListener('DOMContentLoaded', () => {
      document.getElementById('headerImage').classList.add('show');
      let saved;
      try {
        saved = JSON.parse(localStorage.getItem('uciSettings'));
      } catch (e) {
        console.warn("Données locales corrompues");
        return;
      }
      if (!saved) return;

      document.getElementById('jumpToContent').checked = saved.jumpToContent || false;
      document.getElementById('remoteMode').checked = saved.remoteMode || false;
      document.getElementById('autoMode').checked = saved.autoMode || false;

      const applyButton = (groupId, value) => {
        const group = document.getElementById(groupId);
        if (!group || !value) return;
        const btn = [...group.children].find(b => b.dataset.value === value);
        if (btn) btn.classList.add('active');
      };

      applyButton('clickDelay', saved.clickDelay);
      applyButton('menuPosition', saved.menuPosition);
      applyButton('autoDelay', saved.autoDelay);
      applyButton('quickNav', saved.quickNav);
    });

    document.querySelectorAll('.radio-group').forEach(group => {
      group.addEventListener('click', e => {
        if (e.target.tagName === 'BUTTON') {
          group.querySelectorAll('button').forEach(btn => btn.classList.remove('active'));
          e.target.classList.add('active');
        }
      });
    });

    document.getElementById('submitBtn').addEventListener('click', () => {
      const result = {
        jumpToContent: document.getElementById('jumpToContent').checked,
        remoteMode: document.getElementById('remoteMode').checked,
        clickDelay: document.querySelector('#clickDelay .active')?.dataset.value,
        autoMode: document.getElementById('autoMode').checked,
        menuPosition: document.querySelector('#menuPosition .active')?.dataset.value,
        autoDelay: document.querySelector('#autoDelay .active')?.dataset.value,
        quickNav: document.querySelector('#quickNav .active')?.dataset.value
      };

      console.log("Paramètres enregistrés :", result);
      alert("Paramètres appliqués avec succès !");
      localStorage.setItem('uciSettings', JSON.stringify(result));
    });

    const tutorialText = `Bienvenue sur l'assistant de confort moteur.
Étape 1 : Choisissez vos options d’accessibilité en activant les cases et boutons.
Étape 2 : Sélectionnez vos préférences pour les clics, la navigation rapide, et le délai automatique.
Étape 3 : Cliquez sur Appliquer les paramètres.
Vos choix seront mémorisés pour votre prochaine visite.`;

    document.getElementById('playTutorialAudio').addEventListener('click', () => {
      if ('speechSynthesis' in window) {
        const utterance = new SpeechSynthesisUtterance(tutorialText);
        utterance.lang = 'fr-FR';
        utterance.rate = 1;
        utterance.pitch = 1;
        speechSynthesis.speak(utterance);
      } else {
        alert("La lecture vocale n’est pas supportée par ce navigateur.");
      }
    });
  </script>
</body>
</html>
