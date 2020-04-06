<template>
  <view class="container">
    <text class="heading">Qui sommes-nous ?</text>
    <text class ="paragraph">Nous sommes Toni, Mimi, Lulu, Pôl, Ben, Tutune, Math et Beber et nous avons
       créé cette application dans le cadre du Lauzhack against Covid-19 en avril 2020.
        Notre but est que toute personne qui le désire puisse, grâce à notre application,
         transformer son ennui pendant le confinement en un moment où iel apprend plein
          de choses productives et funs.     </text>
    <text class ="signature">Des bisous, huit neuvièmes d'Abiskopains</text>
  </view>
</template>

<style>
.container {
    background-color : #fedff2;
    min-height: 100%;
    min-width: 100%;

    /* Set up proportionate scaling */
    width: 100%;
    height: auto;

    /* Set up positioning */
    align-items : center;
    justify-content : center;
    flex: 1;
  /* background-color: #fedff2;
  color: #f9b7e0;
  font-size: 52;
  display: flex;
  width: auto;
  height: auto;

  display: flex;
  flex-wrap: wrap;
  align-content: center; */

}
.heading {
  font-size: 30px;
  font-weight: bold;
  color: black;
  margin: 20px;
}
.paragraph{
  justify-content : center;
  align-items : center;
  margin: 10% ;
}
.signature{
  right : 1;
}
</style>
