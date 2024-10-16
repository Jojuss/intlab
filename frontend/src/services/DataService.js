import axios from 'axios';

function toJSON(data) {
    const jsonObj = {};
    const fields = Object.getOwnPropertyNames(data);
    for (const field of fields) {
        if (data[field] === undefined) {
            continue;
        }
        jsonObj[field] = data[field];
    }
    return jsonObj;
}

export default class DataService {
    static dataUrlPrefix = 'http://localhost:8080/api';

    static async readAll(url, transformer) {
        const response = await axios.get(this.dataUrlPrefix + url);
        return response.data.map(item => transformer(item));
    }

    static async read(url, transformer) {
        const response = await axios.get(this.dataUrlPrefix + url);      
        return transformer(response.data);
    }

    static async create(url, data) {
        const response = await axios.post(this.dataUrlPrefix + url);
        return true;
    }

    static async update(url, data) {  
        const response = await axios.put(this.dataUrlPrefix + url);
        return true;
    }

    static async delete(url) {
        const response = await axios.delete(this.dataUrlPrefix + url);
        return response.data.id;
    }
}