
export const validar = (cedula: string) => {

   
  let cad = cedula;
    let total = 0;
    let longitud = cad.length;
    let longcheck = longitud - 1;
    if(cedula.length==10){
      for (let i = 0; i < longcheck; i++) {
        if (i % 2 === 0) {
          let aux = +cad.charAt(i) * 2;
          if (aux > 9) aux -= 9;
          total += aux;
        } else {
          total += parseInt(cad.charAt(i)); // parseInt o concatenará en lugar de sumar
        }
      }

      total = total % 10 ? 10 - total % 10 : 0;

      if (+cad.charAt(longitud - 1) == total) {
        return true
      } else {
        return false
      }
    }else{
      return false
    }
      
  

  
  }

