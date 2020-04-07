<template>
<scroll-view class="scroll">
    <view class="container" v-for="(element, index) in content" :key="index">
      <text class="title">{{element.title}}</text>
      <text v-if="element.text" class="para">{{element.text}}</text>
      <view v-for="(link, index_2) in element.links" :key="index_2">
        <href color = "#eea8c4" v-bind:url="link.url" v-bind:name="link.name" />
      </view>
    </view>
</scroll-view>
</template>

<style>
.container {
  align-items: center;
  justify-content: center;
  flex: 1;
}
.title {
  color: white;
  font-size : 20;
  margin : 2%;
  width: 80%;
  font-weight: 900;
}
.para {
  color: white;
  font-size: 15;
  width : 80%;
}
.scroll{
  background-color: #980740; 
}
.gestes_barrières {
  color: white;
  font-size : 20;
  margin : 2%;
}
.para_g_b {
  color: white;
  font-size: 15;

}
.confinement {
  color: white;
  font-size : 20;
  margin : 5%;
}
.ressources{
  color: white;
  font-size : 20;
  position: absolute;
  top: 360px;
  left : 10px
}
.virus{
  border-color : black;
  background-color: #4CAF50; 
  padding : 100px;
  width:90px;
  height :90px;
  position : absolute;
  top : 500px;
  left: 10px;
}

</style>
<script>
import href from "../href.vue";
export default {
  components : {
    href
  },
  data: function() {
    return {
        content: [
        {
          title:"Les Gestes Barrières",
          text:"Il est important de régulièrement se laver les mains, tousser dans son coude ou dans un mouchoir à usage unique et le jeter, éviter de serrer la main et de se tenir à moins d'un mètre d'autrui.",
          links:[{url:"https://www.who.int/fr/emergencies/diseases/novel-coronavirus-2019/advice-for-public", name:"Gestes barrières"}]
        },
        {
          title:"Pourquoi se confiner ",
          text:"Se confiner vous protège vous, mais aussi vos proches ainsi que le reste de la population. Le confinement aide à ne pas surchager les hopitaux. En restant chez vous, vous sauvez des vies. ",
        },
        {
          title:"En finir avec les idées reçues :  ",
          text:"Beaucoup de fausses informations à propos du nouveau coronavirus sont en circulation, pour démêler le vrai du faux : ",
          links:[{url:"https://www.who.int/fr/emergencies/diseases/novel-coronavirus-2019/advice-for-public/myth-busters", name:"Liste de fausses informations"}]
        },
        {
          title:"Ressources",
          text:"Situation",
          links:[{url:"https://www.bag.admin.ch/bag/fr/home/krankheiten/ausbrueche-epidemien-pandemien/aktuelle-ausbrueche-epidemien/novel-cov/situation-schweiz-und-international.html", name:"En Suisse et à l'étranger"},
          {url:"https://solidarites-sante.gouv.fr/soins-et-maladies/maladies/maladies-infectieuses/coronavirus/etat-des-lieux-et-actualites/", name:"En France et à l'étranger"}]
        },
        {
          title:"Si je pense être malade",
          text:"Est-ce que j'ai le coronavirus?",
          links:[{url:"https://ofsp-coronavirus.ch/check/", name:"Suisse"},
          {url:"https://solidarites-sante.gouv.fr/soins-et-maladies/maladies/maladies-infectieuses/coronavirus/tout-savoir-sur-le-covid-19/article/est-ce-que-j-ai-le-coronavirus-429321", name:"France"}]
        },
        {
          title:"Je suis malade, que faire?",
          links:[{url:"https://www.bag.admin.ch/bag/fr/home/krankheiten/ausbrueche-epidemien-pandemien/aktuelle-ausbrueche-epidemien/novel-cov/selbst-isolierung-und-selbst-quarantaene.html", name:"Suisse"},
          {url:"https://solidarites-sante.gouv.fr/soins-et-maladies/maladies/maladies-infectieuses/coronavirus/tout-savoir-sur-le-covid-19/article/j-ai-des-symptomes-je-suis-malade-covid-19", name:"France"}]
        },
        {
          title:"Si j'ai besoin de parler à quelqu'un",
          text:"L'isolement peut être compliqué moralement. Il ne faut pas rester seul.e face à des idées noires. Appelez le 143 (Suisse), le 01 47 07 24 24 (France), ou consultez ces liens :",
          links:[{url:"https://www.santepsy.ch/fr/", name:"Suisse"},
          {url:"http://www.psycom.org/Urgences-psychiatriques#qfecdu", name:"France"}]
        },
        {
          title:"Si je me sens en danger à la maison, je cherche de l'aide ou de la protection",
          text:"Il est déconseillé de sortir, il n'est pas interdit de fuir. Victime de violence? En cas d'urgence, appelez le 117 (Suisse). France : appelez le 3919 ou le 17 en cas d'urgence",
          links:[{url:"https://www.aide-aux-victimes.ch/en/where-can-i-find-support/", name:"Aide aux victimes (Suisse)"}]
        },
        ],
    }
  }
}
</script>
