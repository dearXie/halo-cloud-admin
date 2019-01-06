import moment from 'moment';
class Date2 extends Date{
    static format = (date,str) =>{
       return new moment(date).format(str);
    }

    static format
}

export default Date2