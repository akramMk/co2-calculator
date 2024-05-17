function result() {
  if (!isConnected()) {
    location.href = "calculer.html";
  }

  let userLogin = window.localStorage.getItem("userlogin");

  GETApi("result/" + userLogin, function (data, textStatus, xhr) {
    if (xhr.status === 200) {
      console.log("Données du résultat:", data);

      let transport = data.transport;
      let alimentation = data.alimentation;
      let logement = data.logement;
      let divers = data.divers;
      let serviceSocietaux = data.serviceSocietaux;
      let total = transport + alimentation + logement + divers + serviceSocietaux;

      document.getElementById('result').textContent = 'Mon empreinte carbone par jour: ' + total + ' tCO2eq';
      document.getElementById('transport-quantite').textContent = transport + 'T';
      document.getElementById('alimentation-quantite').textContent = alimentation + 'T';
      document.getElementById('logement-quantite').textContent = logement + 'T';
      document.getElementById('divers-quantite').textContent = divers + 'T';
      document.getElementById('services-sociaux-quantite').textContent = serviceSocietaux + 'T';


    }
  })
}

result();